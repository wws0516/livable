package com.chuangshu.livable.controller;

import com.chuangshu.livable.base.ResultUtil;
import com.chuangshu.livable.base.dto.ResultDTO;
import com.chuangshu.livable.entity.House;
import com.chuangshu.livable.entity.Looking;
import com.chuangshu.livable.entity.User;
import com.chuangshu.livable.service.LookingService;
import com.chuangshu.livable.service.redis.UserRedisService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/looking")
public class LookingController {

    @Autowired
    LookingService lookingService;

    @Autowired
    UserRedisService userRedisService;

    @PostMapping("/insertLooking")
    @ApiOperation("新增约看")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "userID", dataType = "Integer", required = true, value = "用户ID"),
            @ApiImplicitParam(paramType = "query", name = "houseID", dataType = "Integer", required = true, value = "房源ID"),
            @ApiImplicitParam(paramType = "query", name = "data", dataType = "datatime", required = true, value = "时间")
    })
    public ResultDTO insertLooking(Looking looking) throws Exception {
        Looking saveLooking = null;
        User user = new User();
        user.setUserId(looking.getUserId().toString());
        House house = new House();
        house.setHouseId(looking.getHouseId());
        //修改redis中user的值
        userRedisService.userLookHouse(user,house);
        try {
            saveLooking = lookingService.save(looking);
        } catch (Exception e) {
            ResultUtil.Error("500","新增约看失败："+e.getMessage());
        }
        return ResultUtil.Success(saveLooking);
    }

    @DeleteMapping("/deleteLooking")
    @ApiOperation("删除约看")
    @ApiImplicitParam(paramType = "query", name = "lookingID", dataType = "Integer", required = true, value = "约看信息ID")
    public ResultDTO deleteLooking(Integer lookingID){
        try {
            lookingService.deleteById(lookingID);
        } catch (Exception e) {
            ResultUtil.Error("500","删除约看失败："+e.getMessage());
        }
        return ResultUtil.Success();
    }
}
