package com.chuangshu.livable.service.redis;

import com.chuangshu.livable.entity.Allocation;
import com.chuangshu.livable.entity.Feature;
import com.chuangshu.livable.entity.House;
import com.chuangshu.livable.redis.HouseRedisDTO;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.List;


public interface HouseRedisService extends RedisService {

    /**
     * 存储值
     * @param house 房子
     * @param feature
     * @param allocation
     * @return生成的houseDTO
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public HouseRedisDTO setHouseDTO(House house, Feature feature, Allocation allocation) throws InvocationTargetException, IllegalAccessException;

    /**
     * 获取所有房子
     * @return
     */
    public List<HouseRedisDTO> getHouseDTOList();


}
