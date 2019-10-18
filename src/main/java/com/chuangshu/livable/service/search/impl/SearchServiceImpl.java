package com.chuangshu.livable.service.search.impl;

import com.chuangshu.livable.dto.AddressDTO;
import com.chuangshu.livable.dto.HouseBucketDTO;
import com.chuangshu.livable.entity.Address;
import com.chuangshu.livable.entity.BaiduMapLocation;
import com.chuangshu.livable.entity.House;
import com.chuangshu.livable.service.AddressService;
import com.chuangshu.livable.service.HouseService;
import com.chuangshu.livable.service.search.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.DeleteByQueryAction;
import org.elasticsearch.index.reindex.DeleteByQueryRequestBuilder;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: wws
 * @Date: 2019-09-23 09:19
 */
@Service
public class SearchServiceImpl implements ISearchService {

    private static final String INDEX_TOPIC = "house";

    private static final Logger logger = LoggerFactory.getLogger(ISearchService.class);

    static String INDEX_NAME = "livable";

    static String INDEX_TYPE = "house";
    @Autowired
    KafkaTemplate kafkaTemplate;
    @Autowired
    private HouseService houseService;
    @Autowired
    private TransportClient esClient;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private AddressService addressService;

    @KafkaListener(topics = INDEX_TOPIC)
    private void handleMessage(String content){
        HouseIndexMessage houseIndexMessage = null;
        try {
            houseIndexMessage = objectMapper.readValue(content, HouseIndexMessage.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        switch (houseIndexMessage.getOperation()){
            case HouseIndexMessage.INDEX:
                this.createOrUpdateIndex(houseIndexMessage);
                break;
            case HouseIndexMessage.REMOVE:
                this.removeIndex(houseIndexMessage);
                break;
            default:
                logger.warn("Not support message content " + content);
                break;
        }
    }

    public void createOrUpdateIndex(HouseIndexMessage message){

        Integer houseId = message.getHouseId();
        House house = null;
        try {
            house = houseService.get(houseId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (house == null){
            logger.error("Index house {} does not exist!", houseId);
            return;
        }
        HouseIndexTemplate houseIndexTemplate = new HouseIndexTemplate();
        modelMapper.map(house, houseIndexTemplate);

        AddressDTO city = addressService.findByNameAndLevel(house.getCity(), Address.Level.CITY.getValue());
        AddressDTO region = addressService.findByNameAndLevel(house.getRegion(), Address.Level.REGION.getValue());
        String address = city.getName() + region.getName() + house.getAddress();

        BaiduMapLocation baiduMapLocation = addressService.getBaiduMapLocation(city.getName(), address);



        SearchRequestBuilder requestBuilder = this.esClient.prepareSearch(INDEX_NAME).setTypes(INDEX_TYPE)
                .setQuery(QueryBuilders.termQuery("house_id", houseId));

        logger.debug(requestBuilder.toString());
        SearchResponse searchResponse = requestBuilder.get();

        long totalHit = searchResponse.getHits().getTotalHits();

        boolean success;
        if (totalHit == 0){
            success = create(houseIndexTemplate);

        }else if(totalHit == 1) {
            success = update(houseId, houseIndexTemplate);
        }else success = deleteAndCreate(houseId, houseIndexTemplate);
        if (success) {
            logger.debug("Index success with house" + houseId);
        }

        return;
    }

    public void removeIndex(HouseIndexMessage message){

        Integer houseId = message.getHouseId();

        DeleteByQueryRequestBuilder builder = DeleteByQueryAction.INSTANCE
                .newRequestBuilder(esClient)
                .filter(QueryBuilders.termQuery("house_id", houseId))
                .source(INDEX_NAME);

        logger.debug("Delete by query for house: " + builder);

        BulkByScrollResponse response = builder.get();
        long deleted = response.getDeleted();
        if (deleted <= 0) {
            this.remove(houseId, message.getRetry()+1);
        }
    }


    @Override
    public void index(Integer houseId) {
        index(houseId, 0);
    }

    public void index(Integer houseId, int retry) {
        if (retry > HouseIndexMessage.MAX_RETRY) {
            logger.error("Retry index times over 3 for house " + houseId + "Please check it!");
            return;
        }

        HouseIndexMessage message = new HouseIndexMessage(houseId, HouseIndexMessage.INDEX, retry);
        try {
            kafkaTemplate.send(INDEX_TOPIC, objectMapper.writeValueAsString(message));
        } catch (JsonProcessingException e) {
            logger.error("Json encode error for " + message);
        }

    }


    @Override
    public void remove(Integer houseId) {
        remove(houseId, 0);
    }

    public void remove(Integer houseId, int retry){
            if (retry > HouseIndexMessage.MAX_RETRY) {
                logger.error("Retry index times over 3 for house " + houseId + "Please check it!");
                return;
            }

        HouseIndexMessage message = new HouseIndexMessage(houseId, HouseIndexMessage.REMOVE, retry);
        try {
            kafkaTemplate.send(INDEX_TOPIC, objectMapper.writeValueAsString(message));
        } catch (JsonProcessingException e) {
            logger.error("Json encode error for " + message, e);
        }
    }

    @Override
    public List<Integer> countAll(RentSearch rentSearch){
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        boolQuery.filter(
                QueryBuilders.termQuery("area", rentSearch.getCity())
        );

        /**
         * 搜索面积区间的房源
         */
        RentValueBlock acreage = RentValueBlock.matchAcreage(rentSearch.getAcreageBlock());
        if (!RentValueBlock.ALL.equals(acreage)){
            RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery("acreage");
            if (acreage.getMax() > 0){
                rangeQueryBuilder.lte(acreage.getMax());
            }
            if (acreage.getMin() > 0){
                rangeQueryBuilder.gte(acreage.getMax());
            }
            boolQuery.filter(rangeQueryBuilder);
        }

        RentValueBlock price = RentValueBlock.matchPrice(rentSearch.getPriceBlock());
        if (!RentValueBlock.ALL.equals(price)){
            RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery("rent");
            if (price.getMax() > 0){
                rangeQueryBuilder.lte(price.getMax());
            }
            if (price.getMin() > 0){
                rangeQueryBuilder.gte(price.getMax());
            }
            boolQuery.filter(rangeQueryBuilder);
        }

        if (rentSearch.getRentWay() != null) {
            boolQuery.filter(
                    QueryBuilders.termQuery("rentWay", rentSearch.getRentWay())
            );
        }

        if (rentSearch.getFeature() != null){
            QueryBuilders.termQuery("feature", rentSearch.getFeature());
        }

        boolQuery.must(
                QueryBuilders.multiMatchQuery(rentSearch.getKeywords(),
                        "title",
                        "address",
                        "house_type",
                        "layout",
                        "allocation",
                        "introduction"
                        )
        );

        SearchRequestBuilder requestBuilder = this.esClient.prepareSearch(INDEX_NAME)
                .setTypes(INDEX_TYPE)
                .setQuery(boolQuery);
//                .addSort(
//                        rentSearch.getOrderBy(),
//                        SortOrder.fromString(rentSearch.getOrderDirection())
//                );

        logger.debug(requestBuilder.toString());

        List<Integer> houseIds = new ArrayList<>();
        SearchResponse searchResponse = requestBuilder.get();
        if (searchResponse.status() != RestStatus.OK) {
            logger.warn("Search status is not OK for " + requestBuilder);
            return houseIds;
        }
        for (SearchHit searchHit : searchResponse.getHits() ){
            HouseIndexTemplate houseIndexTemplate = null;
            try {
                houseIndexTemplate = objectMapper.readValue(searchHit.getSourceAsString(), HouseIndexTemplate.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
            houseIds.add(houseIndexTemplate.getHouseId());
        }
        return houseIds;
    }

    /**
     * 搜索以地图地区聚合的房源
     * @param cityName
     * @return
     */
    @Override
    public List<HouseBucketDTO> mapAggregate(String cityName) {
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        boolQuery.filter(QueryBuilders.termQuery(HouseIndexKey.CITY, cityName));
        TermsAggregationBuilder agg_region = AggregationBuilders.terms("agg_region").field(HouseIndexKey.REGION);

        SearchRequestBuilder requestBuilder = this.esClient.prepareSearch(INDEX_NAME)
                .setTypes(INDEX_TYPE)
                .setQuery(boolQuery)
                .addAggregation(agg_region);

        logger.debug(requestBuilder.toString());
        SearchResponse searchResponse = requestBuilder.get();
        List<HouseBucketDTO> buckets = new ArrayList<>();
        if (searchResponse.status() != RestStatus.OK){
            logger.warn("Aggregate status is not ok for " + requestBuilder);
            return new ArrayList<>();
        }
        Terms terms = searchResponse.getAggregations().get("agg_region");
        for (Terms.Bucket bucket : terms.getBuckets())
            buckets.add(new HouseBucketDTO(bucket.getKeyAsString(), bucket.getDocCount()));
        return buckets;
    }

    @Override
    public List<Integer> mapQuery(String cityName, String orderBy, String orderDirection, int start, int size) {
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        boolQuery.filter(QueryBuilders.termQuery(HouseIndexKey.CITY, cityName));

        SearchRequestBuilder searchRequestBuilder = this.esClient.prepareSearch(INDEX_NAME)
                .setTypes(INDEX_TYPE)
                .setQuery(boolQuery)
                .addSort(orderBy, SortOrder.fromString(orderDirection))
                .setFrom(start)
                .setSize(size);

        List<Integer> houseIds = new ArrayList<>();
        SearchResponse response = searchRequestBuilder.get();
        if (response.status() != RestStatus.OK) {
            logger.warn("Search status is not ok for " + searchRequestBuilder);
            return houseIds;
        }

        for (SearchHit searchHit : response.getHits()) {
            try {
                houseIds.add(objectMapper.readValue(searchHit.getSourceAsString(), HouseIndexTemplate.class).getHouseId());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return houseIds;
    }

    /**
     * 创建索引
     * @param houseIndexTemplate
     * @return
     */
    private boolean create(HouseIndexTemplate houseIndexTemplate) {
        try {
            IndexResponse response = this.esClient.prepareIndex(INDEX_NAME, INDEX_TYPE)
                    .setSource(objectMapper.writeValueAsBytes(houseIndexTemplate), XContentType.JSON).get();
            logger.debug("Create index with house: " + houseIndexTemplate.getHouseId());
            if (response.status() == RestStatus.CREATED) {
                return true;
            } else return false;
        } catch (JsonProcessingException e) {
            logger.error("Error to index house " + houseIndexTemplate.getHouseId(), e);
            return false;
        }
    }

    /**
     * 更新索引
     * @param houseId
     * @param houseIndexTemplate
     * @return
     */
    private boolean update(Integer houseId, HouseIndexTemplate houseIndexTemplate) {
        try {
            UpdateResponse response = this.esClient.prepareUpdate(INDEX_NAME, INDEX_TYPE, String.valueOf(houseId))
                    .setDoc(objectMapper.writeValueAsBytes(houseIndexTemplate), XContentType.JSON).get();
            logger.debug("Update index with house: " + houseIndexTemplate.getHouseId());
            if (response.status() == RestStatus.OK) {
                return true;
            } else return false;
        } catch (JsonProcessingException e) {
            logger.error("Error to update index house " + houseIndexTemplate.getHouseId(), e);
            return false;
        }
    }

    /**
     * 删除并创建索引
     * @param totalHit
     * @param houseIndexTemplate
     * @return
     */
    private boolean deleteAndCreate(Integer totalHit, HouseIndexTemplate houseIndexTemplate){
        DeleteByQueryRequestBuilder builder = DeleteByQueryAction.INSTANCE
                .newRequestBuilder(esClient)
                .filter(QueryBuilders.termQuery("house_id", houseIndexTemplate.getHouseId()))
                .source(INDEX_NAME);

        logger.debug("Delete by query for house: " + builder);

        BulkByScrollResponse response = builder.get();
        long deleted = response.getDeleted();
        if (deleted != totalHit) {
            logger.warn("Need delete {}, but {} was deleted!", totalHit, deleted);
            return false;
        } else {
            return create(houseIndexTemplate);
        }
    }

}
