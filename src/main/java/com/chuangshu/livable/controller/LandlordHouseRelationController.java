package com.chuangshu.livable.controller;

import com.chuangshu.livable.base.util.ResultUtil;
import com.chuangshu.livable.base.dto.ResultDTO;
import com.chuangshu.livable.dto.RelationDTO;
import com.chuangshu.livable.entity.House;
import com.chuangshu.livable.entity.LandlordHouseRelation;
import com.chuangshu.livable.entity.LandlordInformation;
import com.chuangshu.livable.service.HouseService;
import com.chuangshu.livable.service.LandlordHouseRelationService;
import com.chuangshu.livable.service.LandlordInformationService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class LandlordHouseRelationController {

    @Autowired
    private LandlordHouseRelationService landlordHouseRelationService;

    @Autowired
    private LandlordInformationService landlordInformationService;

    @Autowired
    private HouseService houseService;

    @GetMapping("/getRelationById")
    @ApiOperation("用id得到房源房东关系表")
    @ApiImplicitParam(paramType = "query", name = "relationId", dataType = "Integer", required = true, value = "关系表ID")
    public ResultDTO getRelationById(Integer relationId){
        LandlordHouseRelation landlordHouseRelation = null;
        try {
            landlordHouseRelation = landlordHouseRelationService.get(relationId);
        } catch (Exception e) {
            return ResultUtil.Error("500","意料之外的错误");
        }
        return ResultUtil.Success(landlordHouseRelation);
    }




    @GetMapping("/getLandlordByHouseId")
    @ApiOperation("用房源id得到房东信息")
    @ApiImplicitParam(paramType = "query", name = "houseId", dataType = "Integer", required = true, value = "房源ID")
    public ResultDTO getRelationByHouseId(Integer houseId)throws Exception{
        List<LandlordHouseRelation> landlordHouseRelation = new ArrayList<>();
        LandlordHouseRelation find = new LandlordHouseRelation();
        find.setHouseId(houseId);
        landlordHouseRelation = landlordHouseRelationService.findByParams(find);
        return ResultUtil.Success(landlordInformationService.get(landlordHouseRelation.get(0).getUserId()));
    }



    @GetMapping("/getRelationByUserId")
    @ApiOperation("用用户id得到房源房东关系表")
    @ApiImplicitParam(paramType = "query", name = "userId", dataType = "Integer", required = true, value = "用户ID")
    public ResultDTO getRelationByUserId(Integer userId)throws Exception{
        List<LandlordHouseRelation> landlordHouseRelation = null;
        LandlordHouseRelation find = new LandlordHouseRelation();
        find.setUserId(userId);
        landlordHouseRelation = landlordHouseRelationService.findByParams(find);
        List<RelationDTO> list = new ArrayList<>();
        for(LandlordHouseRelation l: landlordHouseRelation){
            House h = houseService.get(l.getHouseId());
            list.add(new RelationDTO(h.getTitle(),h.getHouseId(),l.getPublishTime()));
        }
        return ResultUtil.Success(list);
    }
}

