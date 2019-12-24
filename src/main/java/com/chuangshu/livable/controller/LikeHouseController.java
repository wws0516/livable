package com.chuangshu.livable.controller;

import com.chuangshu.livable.base.dto.ResultDTO;
import com.chuangshu.livable.base.util.ResultUtil;
import com.chuangshu.livable.dto.LikeHouseDTO;
import com.chuangshu.livable.entity.House;
import com.chuangshu.livable.entity.LikeHouse;
import com.chuangshu.livable.service.HouseService;
import com.chuangshu.livable.service.LikeHouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/like")
public class LikeHouseController {

    @Autowired
    private LikeHouseService likeHouseService;

    @Autowired
    private HouseService houseService;


    @PostMapping()
    public ResultDTO likeHouse(Integer houseId) throws  Exception{
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        Integer userId = null;
        try {
            userId = Integer.parseInt(request.getSession().getAttribute("userID").toString());
        } catch (Exception e) {
            return ResultUtil.Error("500","请先登录");
        }
        if(houseService.get(houseId)==null){
            return ResultUtil.Error("500","无此房源");
        }
        LikeHouse likeHouse = new LikeHouse();
        likeHouse.setUserId(userId);
        likeHouse.setHouseId(houseId);
        LikeHouse returnLikeHouse = null;
        if(likeHouseService.findByParams(likeHouse).size()!=0){
            return ResultUtil.Error("500","已喜欢");
        }
        try {
            returnLikeHouse = likeHouseService.save(likeHouse);
        } catch (Exception e) {
            return ResultUtil.Error("500","添加失败");
        }
        return ResultUtil.Success(returnLikeHouse);
    }

    @DeleteMapping
    public ResultDTO unlikeHouse(Integer houseId){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        Integer userId = null;
        try {
            userId = Integer.parseInt(request.getSession().getAttribute("userID").toString());
        } catch (Exception e) {
            return ResultUtil.Error("500","请先登录");
        }
        LikeHouse likeHouse = new LikeHouse();
        likeHouse.setHouseId(houseId);
        likeHouse.setUserId(userId);

        try {
            likeHouseService.delete(likeHouse);
        } catch (Exception e) {
            return ResultUtil.Error("500","无此记录");
        }

        return ResultUtil.Success();
    }

    @GetMapping("/getAll")
    public ResultDTO getAllLikeHouse() throws  Exception{
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        Integer userId = null;
        try {
            userId = Integer.parseInt(request.getSession().getAttribute("userID").toString());
        } catch (Exception e) {
            return ResultUtil.Error("500","请先登录");
        }
        LikeHouse likeHouse = new LikeHouse();
        likeHouse.setUserId(userId);
        List<LikeHouse> returnList = null;
        try {
            returnList = likeHouseService.findByParams(likeHouse);
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<LikeHouseDTO> likeHouseDTOS = new ArrayList<>();
        for(LikeHouse l:returnList){
            House house = houseService.get(l.getHouseId());
            likeHouseDTOS.add(new LikeHouseDTO(house.getPicture(),house.getAddress(),house.getRent(),l.getDate()));
        }
        return ResultUtil.Success(likeHouseDTOS);

    }
}
