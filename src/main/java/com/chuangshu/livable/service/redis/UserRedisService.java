package com.chuangshu.livable.service.redis;

import com.chuangshu.livable.entity.House;
import com.chuangshu.livable.entity.User;
import com.chuangshu.livable.redis.HouseRedisDTO;

import java.util.List;

/**
 * @auther zhang
 * @ TO DO
 * @creatTime 2019/11/8 17:00
 **/
public interface UserRedisService {

    /**
     * 新增用户，主要拿userid用
     * @param user
     * @return
     * @throws Exception
     */
    public HouseRedisDTO setNewUser(User user) throws  Exception;

    /**
     * 用户看房，修改用户的的参数
     * @param user
     * @param house
     * @return
     * @throws Exception
     */
    public HouseRedisDTO userLookHouse(User user, House house)throws  Exception;

    /**
     * 获取推荐房子的id列表
     * @param user
     * @return
     */
    public List<Integer> userGetRecommend(User user)throws Exception;

}
