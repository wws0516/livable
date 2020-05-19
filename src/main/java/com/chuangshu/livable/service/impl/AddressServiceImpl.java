package com.chuangshu.livable.service.impl;

import com.chuangshu.livable.base.service.impl.BaseServiceImpl;
import com.chuangshu.livable.dto.AddressDTO;
import com.chuangshu.livable.dto.LevelAddressDTO;
import com.chuangshu.livable.dto.LevelBelongToAddressDTO;
import com.chuangshu.livable.dto.NameLevelAddressDTO;
import com.chuangshu.livable.entity.Address;
import com.chuangshu.livable.utils.baiduMapUtil.BaiduMapLocation;
import com.chuangshu.livable.mapper.AddressMapper;
import com.chuangshu.livable.service.AddressService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class AddressServiceImpl extends BaseServiceImpl<AddressMapper, Address> implements AddressService {

    private static final String BAIDU_MAP_KEY = "WFcr4SZwZgS9XPwWqR0DBtGNoWKGUsGj";
    private static final String BAIDU_MAP_SK = "1KpmrjNu48Zns8W5ARLY2jLeae5SC8lQ";
    private static final String BAIDU_MAP_GEOCONV_API = "http://api.map.baidu.com/geocoding/v3/?";
    private static final String BAIDU_POI_CREATE = "http://api.map.baidu.com/geodata/v3/poi/create";
    private static final String BAIDU_POI_UPDATE = "http://api.map.baidu.com/geodata/v3/poi/update";
    private static final String BAIDU_POI_DELETE = "http://api.map.baidu.com/geodata/v3/poi/delete";
    private static final String BAIDU_POI_LIST = "http://api.map.baidu.com/geodata/v3/poi/list?";

    private static final Logger logger = LoggerFactory.getLogger(AddressService.class);

    @Autowired
    ObjectMapper objectMapper;

    @Override
    public List<AddressDTO> findAllByLevel(String level) {
        AddressDTO levelAddressDTO = new LevelAddressDTO(level);
        try {
            return super.findByParams(levelAddressDTO, AddressDTO.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public AddressDTO findByNameAndLevel(String name, String level) {
        AddressDTO nameLevelAddressDTO = new NameLevelAddressDTO(name, level);
        try {
            return super.findByParams(nameLevelAddressDTO, AddressDTO.class).get(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public AddressDTO findByNameAndBelongTo(String name, String belongTo) {
        return null;
    }

    @Override
    public List<AddressDTO> findAllByLevelAndBelongTo(String level, String belongTo) {
        AddressDTO levelBelongToAddressDTO = new LevelBelongToAddressDTO(belongTo, level);
        try {
            return super.findByParams(levelBelongToAddressDTO, AddressDTO.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public BaiduMapLocation getBaiduMapLocation(String city, String address) {
        String encodeAddress = null;
        String encodeCity = null;

        try {
            encodeAddress = URLEncoder.encode(address, "UTF-8");
            encodeCity = URLEncoder.encode(city, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            logger.error("Error to encode house's address!");
            e.printStackTrace();
        }

        HttpClient httpClient = HttpClients.createDefault();

//        //生成sn
//        String sn = null;
//        SnUtil snUtil = new SnUtil();
//        try {
//            sn = snUtil.snCal(address, BAIDU_MAP_KEY, BAIDU_MAP_SK);
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        }

        //生成访问URl
        StringBuilder sb = new StringBuilder(BAIDU_MAP_GEOCONV_API);
        sb.append("address=").append(encodeAddress).append("&")
                .append("output=json&")
                .append("ak=").append(BAIDU_MAP_KEY);

        //进行get请求
        HttpGet get = new HttpGet(sb.toString());
        try {
            HttpResponse httpResponse = httpClient.execute(get);
            if (httpResponse.getStatusLine().getStatusCode() != HttpStatus.SC_OK){
                return null;
            }
            String result = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
            JsonNode jsonNode = objectMapper.readTree(result);
            if ( jsonNode.get("status").asInt() != 0)
                return null;
            else {
                BaiduMapLocation location = new BaiduMapLocation();
                JsonNode jsonLocation = jsonNode.get("result").get("location");
                location.setLongitude(jsonLocation.get("lng").asDouble());
                location.setLatitude(jsonLocation.get("lat").asDouble());
                return location;
            }
        } catch (IOException e) {
            logger.error("Error to fetch BaiduMap API", e);
            return null;
        }
    }
//    来自stackoverflow的MD5计算方法，调用了MessageDigest库函数，并把byte数组结果转换成16进制
//    public String MD5(String md5)
//    {
//        try
//        {
//            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
//            byte[] array = md.digest(md5.getBytes());
//            StringBuffer sb = new StringBuffer();
//            for (int i = 0; i < array.length; ++i)
//            {
//                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
//            }
//            return sb.toString();
//        } catch (java.security.NoSuchAlgorithmException e)
//        {
//            e.printStackTrace();
//        }
//        return null;
//    }

    @Override
    public List<AddressDTO> findAllCities(String cityName) {

        List<AddressDTO> cities = findAllByLevel(Address.Level.CITY.getValue());
        return cities;
    }

    @Override
    public Map<Address.Level, AddressDTO> findCityAndRegion(String cityName, String regionName) {
        Map<Address.Level, AddressDTO> result = new HashMap<>();
        AddressDTO city = findByNameAndLevel(cityName, Address.Level.CITY.getValue());
        AddressDTO region = findByNameAndBelongTo(regionName, city.getName());

        result.put(Address.Level.CITY, city);
        result.put(Address.Level.REGION, region);
        return result;
    }

    @Override
    public List<AddressDTO> findAllRegionsByCityName(String cityName) {
        if (cityName == null) {
            return new ArrayList<>();
        }
        List<AddressDTO> regions = findAllByLevelAndBelongTo(Address.Level.REGION
                .getValue(), cityName);

        return regions;
    }

    @Override
    public AddressDTO findCity(String cityName) {
        if (cityName == null) {
            return null;
        }
        AddressDTO city = findByNameAndLevel(cityName, Address.Level.CITY.getValue());
        if (city == null) {
            return null;
        }
        return city;
    }

    @Override
    public boolean lbsUpdate(BaiduMapLocation baiduMapLocation, String title, Integer rent, Integer houseId, String address, Integer acreage) {

        HttpClient httpClient = HttpClients.createDefault();

        List<NameValuePair> nvps = new ArrayList<>();
        nvps.add(new BasicNameValuePair("latitude", String.valueOf(baiduMapLocation.getLatitude())));
        nvps.add(new BasicNameValuePair("longitude", String.valueOf(baiduMapLocation.getLongitude())));
        nvps.add(new BasicNameValuePair("coord_type", "3"));
        nvps.add(new BasicNameValuePair("geotable_id", "206952"));
        nvps.add(new BasicNameValuePair("ak", BAIDU_MAP_KEY));
        nvps.add(new BasicNameValuePair("title", title));
        nvps.add(new BasicNameValuePair("address", address));
        nvps.add(new BasicNameValuePair("houseId", houseId.toString()));
        nvps.add(new BasicNameValuePair("acreage", acreage.toString()));
        nvps.add(new BasicNameValuePair("rent", rent.toString()));

        HttpPost httpPost;

        if (isLbsDataExists(houseId))
            httpPost = new HttpPost(BAIDU_POI_UPDATE);
        else
            httpPost = new HttpPost(BAIDU_POI_CREATE);
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
            HttpResponse response = httpClient.execute(httpPost);
            String result = EntityUtils.toString(response.getEntity());
            if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK){
                logger.error("Error to update lbs data for response", result);
                return false;
            }else {
                JsonNode jsonNode = objectMapper.readTree(result);
                if (jsonNode.get("status").asInt() != 0) {
                    logger.error("Error to update lbs data for houseId{} with status {}, message {}", houseId, jsonNode.get("status").asInt(), jsonNode.get("message").asText());
                    return false;
                }
                else
                    return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean isLbsDataExists(Integer houseId){
        HttpClient httpClient = HttpClients.createDefault();

        StringBuilder sb = new StringBuilder(BAIDU_POI_LIST);
        sb.append("geotable_id=206952").append("&").append("ak=").append(BAIDU_MAP_KEY).append("&").append("houseId=").append(houseId).append(',').append(houseId);
        HttpGet httpGet = new HttpGet(sb.toString());
        HttpResponse response = null;
        try {
            response = httpClient.execute(httpGet);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String result = null;
        try {
            result = EntityUtils.toString(response.getEntity());
            System.out.println(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (response.getStatusLine().getStatusCode()!=HttpStatus.SC_OK) {
            logger.error("Error to create lbs data for request{}", result);
            return false;
        }else {
            try {
                JsonNode jsonNode = objectMapper.readTree(result);
                if (jsonNode.get("status").asInt() != 0) {
                    logger.error("Error to create lbs data for houseId{} with status {}, message {}", houseId, jsonNode.get("status").asInt(), jsonNode.get("message").asText());
                    return false;
                } else{
                    if (jsonNode.get("size").asLong()==0)
                        return false;
                    else return true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        }
    }

    @Override
    public boolean removeLbs(Integer houseId){
        HttpClient httpClient = HttpClients.createDefault();

        List<NameValuePair> nvps = new ArrayList<>();
        nvps.add(new BasicNameValuePair("geotable_id", "206952"));
        nvps.add(new BasicNameValuePair("ak", BAIDU_MAP_KEY));
        nvps.add(new BasicNameValuePair("houseId", String.valueOf(houseId)));

        HttpPost delete = new HttpPost(BAIDU_POI_DELETE);


        try {
            delete.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
            HttpResponse response = httpClient.execute(delete);
            String result = EntityUtils.toString(response.getEntity(), "UTF-8");
            if (response.getStatusLine().getStatusCode()!=HttpStatus.SC_OK) {
                logger.error("Error to delete lbs data for request{}", result);
                return false;
            }
            JsonNode jsonNode = objectMapper.readTree(result);
            if (jsonNode.get("status").asInt() != 0) {
                logger.error("Error to delete lbs data for houseId{} with status {}, message {}", houseId, jsonNode.get("status").asInt(), jsonNode.get("message").asText());
                return false;
            } else{
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
            return false;
    }

}
