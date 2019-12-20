package com.chuangshu.livable.controller;

import com.chuangshu.livable.base.dto.ResultDTO;
import com.chuangshu.livable.base.util.ResultUtil;
import com.chuangshu.livable.entity.IdInformation;
import com.chuangshu.livable.entity.PersonalInformation;
import com.chuangshu.livable.service.IdInformationService;
import com.chuangshu.livable.service.PersonalInformationService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/information")
public class PersonalInformationController {

    @Autowired
    private PersonalInformationService personalInformationService;

    @Autowired
    private IdInformationService idInformationService;

    @PostMapping("/personalInformation")
    @ApiOperation("更新个人信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "headPortrait", dataType = "String", required = true, value = "头像"),
            @ApiImplicitParam(paramType = "query", name = "age", dataType = "Integer", required = true, value = "年龄"),
            @ApiImplicitParam(paramType = "query", name = "job", dataType = "String", required = true, value = "职业"),
            @ApiImplicitParam(paramType = "query", name = "hobby", dataType = "String", required = true, value = "爱好")
    })
    public ResultDTO personalInformation(PersonalInformation personalInformation) throws Exception{
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        Integer userId = null;
        try {
            userId = Integer.parseInt(request.getSession().getAttribute("userID").toString());
        } catch (Exception e) {
            return ResultUtil.Error("500","请先登录");
        }
        PersonalInformation personalInformation1 = new PersonalInformation();
        personalInformation1.setUserId(userId);
        PersonalInformation returnPersonalInformation = null;
        if (personalInformationService.findByParams(personalInformation1).size()==0) {
            personalInformation.setUserId(userId);
            returnPersonalInformation = personalInformationService.save(personalInformation);
        }else{
            personalInformation.setUserId(userId);
            personalInformationService.update(personalInformation);
        }
        return ResultUtil.Success(returnPersonalInformation);
    }

    @PostMapping("/IDInformation")
    @ApiOperation("更新证件信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "name", dataType = "String", required = true, value = "真实姓名"),
            @ApiImplicitParam(paramType = "query", name = "idCardType", dataType = "String", required = true, value = "证件类型"),
            @ApiImplicitParam(paramType = "query", name = "idNumber", dataType = "Integer", required = true, value = "证件号"),
            @ApiImplicitParam(paramType = "query", name = "idCardPicZ", dataType = "String", required = true, value = "证件照正面"),
            @ApiImplicitParam(paramType = "query", name = "idCardPicF", dataType = "String", required = true, value = "证件照背面")
    })
    public ResultDTO IDInformation(IdInformation idInformation) throws Exception{
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        Integer userId = null;
        try {
            userId = Integer.parseInt(request.getSession().getAttribute("userID").toString());
        } catch (Exception e) {
            return ResultUtil.Error("500","请先登录");
        }
        IdInformation idInformation1 = new IdInformation();
        idInformation1.setUserId(userId);
        IdInformation returnIdInformation = null;
        if (idInformationService.findByParams(idInformation1).size()==0) {
            idInformation.setUserId(userId);
            returnIdInformation = idInformationService.save(idInformation);
        }else{
            idInformation.setUserId(userId);
            idInformationService.update(idInformation);
        }
        return ResultUtil.Success(returnIdInformation);
    }
}
