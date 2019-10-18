package com.chuangshu.livable.service.impl;

import com.chuangshu.livable.base.SnUtil;
import com.chuangshu.livable.base.service.impl.BaseServiceImpl;
import com.chuangshu.livable.dto.AddressDTO;
import com.chuangshu.livable.dto.LevelAddressDTO;
import com.chuangshu.livable.dto.LevelBelongToAddressDTO;
import com.chuangshu.livable.dto.NameLevelAddressDTO;
import com.chuangshu.livable.entity.Address;
import com.chuangshu.livable.entity.BaiduMapLocation;
import com.chuangshu.livable.mapper.AddressMapper;
import com.chuangshu.livable.service.AddressService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.kafka.common.protocol.types.Field;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class AddressServiceImpl extends BaseServiceImpl<AddressMapper, Address> implements AddressService {

    private static final String BAIDU_MAP_KEY = "WFcr4SZwZgS9XPwWqR0DBtGNoWKGUsGj";
    private static final String BAIDU_MAP_SK = "1KpmrjNu48Zns8W5ARLY2jLeae5SC8lQ";
    private static final String BAIDU_MAP_GEOCONV_API = "http://api.map.baidu.com/geocoding/v3/?";

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
    public List<AddressDTO> findByNameAndBelongTo(String name, String belongTo) {
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

    // 来自stackoverflow的MD5计算方法，调用了MessageDigest库函数，并把byte数组结果转换成16进制
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
}
