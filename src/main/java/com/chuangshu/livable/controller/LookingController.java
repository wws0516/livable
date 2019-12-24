package com.chuangshu.livable.controller;

import com.chuangshu.livable.base.util.ResultUtil;
import com.chuangshu.livable.base.dto.ResultDTO;
import com.chuangshu.livable.dto.LookDTO;
import com.chuangshu.livable.entity.House;
import com.chuangshu.livable.entity.LandlordHouseRelation;
import com.chuangshu.livable.entity.Looking;
import com.chuangshu.livable.entity.User;
import com.chuangshu.livable.service.LandlordHouseRelationService;
import com.chuangshu.livable.service.LookingService;
import com.chuangshu.livable.service.UserService;
import com.chuangshu.livable.service.redis.UserRedisService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/looking")
public class LookingController {

    @Autowired
    LookingService lookingService;

    @Autowired
    UserRedisService userRedisService;

    @Autowired
    private UserService userService;

    @Autowired
    private LandlordHouseRelationService landlordHouseRelationService;

    @PostMapping("/insertLooking")
    @ApiOperation("新增约看")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "userID", dataType = "Integer", required = true, value = "用户ID"),
            @ApiImplicitParam(paramType = "query", name = "houseID", dataType = "Integer", required = true, value = "房源ID"),
            @ApiImplicitParam(paramType = "query", name = "data", dataType = "datatime", required = true, value = "时间"),
            @ApiImplicitParam(paramType = "query", name = "site", dataType = "String", required = true, value = "地点")
    })
    public ResultDTO insertLooking(Looking looking) throws Exception {
        Looking saveLooking = null;
        User user = new User();
        user.setUserId(looking.getUserId());
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

    @GetMapping("/getLooking")
    @ApiOperation("获取约看")
    public ResultDTO getLooking() throws Exception{
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        Integer userId = null;
        userId = Integer.parseInt(request.getSession().getAttribute("userID").toString());
        Looking looking = new Looking();
        looking.setUserId(userId);
        List<Looking> lookingList = lookingService.findByParams(looking);
        List<LookDTO> lookDTOS = new ArrayList<>();
        for(Looking l: lookingList){
            lookDTOS.add(new LookDTO(l,userService.get(userId).getName()));
        }
        return ResultUtil.Success(lookDTOS);
    }

}
