package com.chuangshu.livable.service.redis.impl;

import com.chuangshu.livable.entity.Allocation;
import com.chuangshu.livable.entity.Feature;
import com.chuangshu.livable.entity.House;
import com.chuangshu.livable.redis.HouseRedisDTO;
import com.chuangshu.livable.service.redis.HouseRedisService;
import com.chuangshu.livable.service.redis.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Set;


/**
 * @auther zhang
 * @ TO DO
 * @creatTime 2019/11/7 20:04
 **/
@Service("HouseRedisServiceImpl")
public class HouseRedisServiceImpl extends RedisServiceImpl implements HouseRedisService {



    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Override
    public HouseRedisDTO setHouseDTO(House house, Feature feature, Allocation allocation) throws InvocationTargetException, IllegalAccessException {
        HouseRedisDTO houseRedisDTO = new HouseRedisDTO(house, feature, allocation);
        set("house-"+house.getHouseId(),houseRedisDTO);
        return houseRedisDTO;
    }

    @Override
    public List<HouseRedisDTO> getHouseDTOList() {
        Set<String> keys = redisTemplate.keys("house-" + "*");
        List<Object> objects = redisTemplate.opsForValue().multiGet(keys);
        List tranfor = objects;
        List<HouseRedisDTO> houseRedisDTOList = tranfor;
        return houseRedisDTOList;
    }


}
