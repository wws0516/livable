package com.chuangshu.livable.service;

import com.chuangshu.livable.LivableApplicationTests;
import com.chuangshu.livable.base.util.modelmapper.ToFeature;
import com.chuangshu.livable.dto.HouseBucketDTO;
import com.chuangshu.livable.entity.Address;
import com.chuangshu.livable.entity.Feature;
import com.chuangshu.livable.entity.House;
import com.chuangshu.livable.service.AddressService;
import com.chuangshu.livable.service.FeatureService;
import com.chuangshu.livable.service.HouseService;
import com.chuangshu.livable.service.search.ISearchService;
import com.chuangshu.livable.utils.baiduMapUtil.BaiduMapLocation;
import com.chuangshu.livable.utils.esUtil.HouseIndexTemplate;
import com.chuangshu.livable.utils.esUtil.RentSearch;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.DeleteByQueryAction;
import org.elasticsearch.index.reindex.DeleteByQueryRequestBuilder;
import org.elasticsearch.rest.RestStatus;
import org.junit.Test;
import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @Author: wws
 * @Date: 2019-09-23 13:32
 */

public class SearchServiceTests extends LivableApplicationTests {

    @Autowired
    private ISearchService searchService;

    @Autowired
    private AddressService addressService;

    @Autowired
    private HouseService houseService;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    FeatureService featureService;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private TransportClient esClient;

    @Autowired
    ToFeature toFeature;

    private static final Logger logger = LoggerFactory.getLogger(ISearchService.class);

    static int mapping = 0;

    @Test
    public void testIndex() throws Exception {

//        System.out.println(featureService);
        House house = houseService.get(3);
//        Address address = addressService.get(440106);
//        addressService.lbsUpdate(addressService.getBaiduMapLocation("广州市", "天河区"),
//                house.getTitle(),
//                Integer.parseInt(house.getRent()),
//                house.getHouseId(),
//                house.getAddress(),
//                Integer.parseInt(house.getAcreage().toString()));
//        addressService.lbsUpdate(addressService.getBaiduMapLocation("广州市", "天河区"),
//                house.getTitle(),
//                Integer.parseInt(house.getRent()),
//                house.getHouseId(),
//                house.getAddress(),
//                Integer.parseInt(house.getAcreage().toString()));
        //        House house = null;
//        Integer houseId=3;
//        try {
//            house = houseService.get(houseId);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        HouseIndexTemplate houseIndexTemplate = new HouseIndexTemplate();

        //创建自定义映射规则
        PropertyMap<House, HouseIndexTemplate> propertyMap = new PropertyMap<House, HouseIndexTemplate>() {
            @Override
            protected void configure() {
                using(toFeature.getToFeature()).map(source.getFeatureId(),destination.getFeature());//使用自定义转换规则
                skip(destination.getSuggests());
            }
        };

        if (mapping<1) {
            //添加映射器
            modelMapper.addMappings(propertyMap);
            mapping++;
        }
        modelMapper.validate();
        houseIndexTemplate = modelMapper.map(house, HouseIndexTemplate.class);

        try {
            UpdateResponse response = this.esClient.prepareUpdate("livable", "house", String.valueOf(3))
                    .setDoc(objectMapper.writeValueAsBytes(houseIndexTemplate), XContentType.JSON).get();
            logger.debug("Update index with house: " + houseIndexTemplate.getHouseId());
            if (response.status() == RestStatus.OK) {
                return;
            } else return;
        } catch (JsonProcessingException e) {
            logger.error("Error to update index house " + houseIndexTemplate.getHouseId(), e);
        }
//        addressService.removeLbs(house.getHouseId());
//        for (int i=3;i<163;i++) {
//            searchService.remove(i);
//            Thread.sleep(3000);
//        }
////

//        System.out.println(houseIndexTemplate);
//
//        try {
//            IndexResponse response = this.esClient.prepareDelete("livable", "house", )
//                    .setSource(objectMapper.writeValueAsBytes(houseIndexTemplate), XContentType.JSON).get();
//            logger.debug("Create index with house: " + houseIndexTemplate.getHouseId());
//            if (response.status() == RestStatus.CREATED) {
//                return;
//            } else return;
//        } catch (JsonProcessingException e) {
//            logger.error("Error to index house " + houseIndexTemplate.getHouseId(), e);
//            return;
//        }
//
//        DeleteByQueryRequestBuilder builder = DeleteByQueryAction.INSTANCE
//                .newRequestBuilder(esClient)
//                .filter(QueryBuilders.termQuery("houseId", 3))
//                .source("livable");
//
//        logger.debug("Delete by query for house: " + builder);
//
//        BulkByScrollResponse response = builder.get();
//        long deleted = response.getDeleted();
//        if (deleted <= 0) {
////            this.remove(houseId, message.getRetry()+1);
//            System.out.println("delete failed");
//        }


//
//
//        Integer targetHouseId = 1;
////        RentSearch rentSearch = new RentSearch();
//////        rentSearch.setArea("北京");
////        rentSearch.setAcreageBlock("50-*");
////        rentSearch.setPriceBlock("1000-3000");
////        rentSearch.setKeywords("租到");
////        List houseIds = searchService.countAll(rentSearch);
//        searchService.index(1);
//        searchService.index(2);
//        HouseController houseController = new HouseController();
//
//        AddressDTO nameLevelAddressDTO = new NameLevelAddressDTO("广州", "city");
//        List<AddressDTO> city = addressService.findByParams(nameLevelAddressDTO, AddressDTO.class);
//        if (city.size() == 0) {
//            System.out.println("cw");
//        }
//        AddressDTO levelBelongToAddressDTO = new LevelBelongToAddressDTO("广州", "region");
//        System.out.println(addressService.findByParams(levelBelongToAddressDTO, AddressDTO.class).size());
//
//        List<HouseBucketDTO> houseBucketDtos = searchService.mapAggregate("广州");
//        System.out.println(houseBucketDtos);
//
//        RentSearch rentSearch = new RentSearch();
//        rentSearch.setCity("广州");
//        rentSearch.setAcreageBlock("30-50");
//        rentSearch.setPriceBlock("3000-*");
//        rentSearch.setKeywords("广场");
//        rentSearch.setFeature(new Feature(0,0,0,0,0,1,0, 0, 0));
//        System.out.println(houseService.query(rentSearch));

    }
}
