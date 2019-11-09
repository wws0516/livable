package com.chuangshu.livable.service.redis.impl;

import com.chuangshu.livable.entity.House;
import com.chuangshu.livable.entity.User;
import com.chuangshu.livable.redis.HouseRedisDTO;
import com.chuangshu.livable.service.redis.UserRedisService;
import com.chuangshu.livable.utils.redisUtil.HouseRedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.*;

/**
 * @auther zhang
 * @ TO DO
 * @creatTime 2019/11/8 17:01
 **/

@Service("UserRedisServiceImpl")
public class UserRedisServiceImpl extends RedisServiceImpl implements UserRedisService {



    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Override
    public HouseRedisDTO setNewUser(User user) throws Exception{
        Set<String> keys = redisTemplate.keys("user-" + "*");
        List<Object> objects = redisTemplate.opsForValue().multiGet(keys);
        List tranfor = objects;
        List<HouseRedisDTO> houseRedisDTOList = tranfor;
        Field[] fields = HouseRedisDTO.class.getDeclaredFields();
        Double[] newfields = new Double[fields.length];
        for (int i = 0; i < newfields.length; i++) {
            newfields[i] = 0d;
        }
        for (int i = 0; i < fields.length; i++) {
            for (HouseRedisDTO houseRedisDTO : houseRedisDTOList) {
                fields[i].setAccessible(true);
                Double aDouble = (Double) fields[i].get(houseRedisDTO);
                newfields[i] += aDouble;
            }
        }
        for (int i = 0; i < newfields.length; i++) {
            newfields[i] /=houseRedisDTOList.size();
        }
        HouseRedisDTO houseRedisDTO = new HouseRedisDTO();
        Field[] declaredFields = houseRedisDTO.getClass().getDeclaredFields();
        for (int i = 0; i < declaredFields.length; i++) {
            declaredFields[i].setAccessible(true);
            declaredFields[i].set(houseRedisDTO,newfields[i]);
        }
        set("user-"+user.getUserId(),houseRedisDTO);
        return houseRedisDTO;
    }

    @Override
    public HouseRedisDTO userLookHouse(User user, House house) throws Exception {
        HouseRedisDTO houseRedisDTO = (HouseRedisDTO) redisTemplate.opsForValue().get("house-"+house.getHouseId());
        HouseRedisDTO userRedisDTO = (HouseRedisDTO) redisTemplate.opsForValue().get("user-"+user.getUserId());
        Field[] houseFields = houseRedisDTO.getClass().getDeclaredFields();
        Field[] userFields = userRedisDTO.getClass().getDeclaredFields();
        Date start = new Date();
        for (int i = 0; i < userFields.length; i++) {
            userFields[i].setAccessible(true);
            houseFields[i].setAccessible(true);
            Double houseData = (Double) houseFields[i].get(houseRedisDTO);
            for (int a =0; a<=5;a++){
                Double userData = (Double) userFields[i].get(userRedisDTO);
                Double newFieldData = userData - 0.2 * (userData - houseData);
                houseFields[i].set(userRedisDTO , newFieldData);
            }
        }
        set("user-"+user.getUserId(),houseRedisDTO);
        return userRedisDTO;
    }

    @Override
    public List<Integer> userGetRecommend(User user) throws Exception {
        //获取所有列表
        Set<String> keys = redisTemplate.keys("house-" + "*");
        List<Object> objects = redisTemplate.opsForValue().multiGet(keys);
        List tranfor = objects;
        List<HouseRedisDTO> houseRedisDTOList = tranfor;
        //获取价格以及面积的最大最小值
        Double renmax = houseRedisDTOList.stream().mapToDouble(HouseRedisDTO::getRent).max().getAsDouble();
        Double renmin = houseRedisDTOList.stream().mapToDouble(HouseRedisDTO::getRent).min().getAsDouble();
        Double arcmax = houseRedisDTOList.stream().mapToDouble(HouseRedisDTO::getAcreage).max().getAsDouble();
        Double arcmin = houseRedisDTOList.stream().mapToDouble(HouseRedisDTO::getAcreage).min().getAsDouble();
        //获取用户实体
        HouseRedisDTO userRedisDTO = (HouseRedisDTO) get("user-" + user.getUserId());
        //房子的索引list
        ArrayList<String> keyslist = new ArrayList<>(redisTemplate.keys("house-" + "*"));
        Map<String, Double> distances = new TreeMap<>();
        for (int i = 0; i < houseRedisDTOList.size(); i++) {
            HouseRedisDTO redisDTO = houseRedisDTOList.get(i);
            Double aDouble = HouseRedisUtil.NormailCosDistance(redisDTO,userRedisDTO,renmax,renmin,arcmax,arcmin);
            distances.put(keyslist.get(i),aDouble);
        }
        List<Map.Entry<String,Double>> list = new ArrayList<Map.Entry<String,Double>>(distances.entrySet());
        //排序
        Collections.sort(list, new Comparator<Map.Entry<String, Double>>(){
            public int compare(Map.Entry<String, Double> map1, Map.Entry<String,Double> map2) {
                return ((map2.getValue() - map1.getValue() == 0) ? 0 : (map2.getValue() - map1.getValue() > 0) ? 1 : -1);
            }
        });
        ArrayList<Integer> resultIdList = new ArrayList<>();
        for (Map.Entry<String, Double> entry : list) {
            resultIdList.add(Integer.valueOf(entry.getKey().split("-")[1]));
        }
        return resultIdList;

    }
}
