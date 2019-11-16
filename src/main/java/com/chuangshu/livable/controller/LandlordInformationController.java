package com.chuangshu.livable.controller;

import com.chuangshu.livable.StatusCode.HouseStatusCode;
import com.chuangshu.livable.base.util.ResultUtil;
import com.chuangshu.livable.base.dto.ResultDTO;
import com.chuangshu.livable.entity.LandlordInformation;
import com.chuangshu.livable.service.LandlordInformationService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class LandlordInformationController {

    @Autowired
    private LandlordInformationService landlordInformationService;

    @ApiOperation("房东注册")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "userId", dataType = "Integer", required = true, value = "用户ID"),
            @ApiImplicitParam(paramType = "query", name = "idNumber", dataType = "Integer", required = true, value = "身份证号"),
            @ApiImplicitParam(paramType = "query", name = "idCardPictureF", dataType = "String", required = true, value = "身份证正面"),
            @ApiImplicitParam(paramType = "query", name = "idCardPictureR", dataType = "String", required = true, value = "身份证反面"),
            @ApiImplicitParam(paramType = "query", name = "alipayName", dataType = "String", required = true, value = "支付宝账户名"),
            @ApiImplicitParam(paramType = "query", name = "alipayAccount", dataType = "String", required = true, value = "支付宝账号")

    })
    @PostMapping("/registerLandlord")
    public ResultDTO registerLandlord(LandlordInformation landlordInformation){
        LandlordInformation landlordInformation1 = null;
        landlordInformation.setStatus(HouseStatusCode.HOUSE_UNCHECKED.getCode().toString());
        try {
            landlordInformation1 = landlordInformationService.save(landlordInformation);
        } catch (Exception e) {
            return ResultUtil.Error("500","意料之外的错误");
        }

        return ResultUtil.Success(landlordInformation1);
    }

    @ApiOperation("审批房东信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "landlordId", dataType = "Integer", required = true, value = "房东ID"),
            @ApiImplicitParam(paramType = "query", name = "code", dataType = "String", required = true, value = "房东信息状态"),
    })
    @GetMapping("/checkLandlord")
    public ResultDTO checkLandlord(Integer landlordId,String code) {
        LandlordInformation landlordInformation = new LandlordInformation();
        landlordInformation.setLandlordId(landlordId);
        if (code.equals("U")) {
            landlordInformation.setStatus(HouseStatusCode.HOUSE_UNCHECKED.getCode().toString());

        }else if(code.equals("C")){
            landlordInformation.setStatus(HouseStatusCode.HOUSE_CHECKED.getCode().toString());
        }
        try {
            landlordInformationService.update(landlordInformation);
        } catch (Exception e) {
            return ResultUtil.Error("500","意料之外的错误");
        }
        return ResultUtil.Success();
    }

    @PostMapping("/updateLandlordInformation")
    @ApiOperation("修改房东信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "landlordId", dataType = "Integer", required = true, value = "房东ID"),
            @ApiImplicitParam(paramType = "query", name = "userId", dataType = "Integer", required = true, value = "用户ID"),
            @ApiImplicitParam(paramType = "query", name = "idNumber", dataType = "Integer", required = true, value = "身份证号"),
            @ApiImplicitParam(paramType = "query", name = "idCardPictureF", dataType = "String", required = true, value = "身份证正面"),
            @ApiImplicitParam(paramType = "query", name = "idCardPictureR", dataType = "String", required = true, value = "身份证反面"),
            @ApiImplicitParam(paramType = "query", name = "alipayName", dataType = "String", required = true, value = "支付宝账户名"),
            @ApiImplicitParam(paramType = "query", name = "alipayAccount", dataType = "String", required = true, value = "支付宝账号")

    })
    public ResultDTO updateLandlordInformation(LandlordInformation landlordInformation){
        try {
            landlordInformationService.update(landlordInformation);
        } catch (Exception e) {
            return ResultUtil.Error("500","意料之外的错误");
        }

        return ResultUtil.Success();
    }

    @GetMapping("/listAllLandlord")
    @ApiOperation("得到所有房东信息")
    public ResultDTO listAllLandlord(){
        List<LandlordInformation> landlordInformationList = null;
        try {
            landlordInformationList = landlordInformationService.findAll();
        } catch (Exception e) {
            return ResultUtil.Error("500","意料之外的错误");
        }
        return ResultUtil.Success(landlordInformationList);
    }

}
