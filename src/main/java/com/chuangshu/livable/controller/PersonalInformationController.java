package com.chuangshu.livable.controller;

import com.chuangshu.livable.base.dto.ResultDTO;
import com.chuangshu.livable.base.util.ResultUtil;
import com.chuangshu.livable.dto.PersonalInformationDTO;
import com.chuangshu.livable.entity.IdInformation;
import com.chuangshu.livable.entity.PersonalInformation;
import com.chuangshu.livable.entity.User;
import com.chuangshu.livable.service.IdInformationService;
import com.chuangshu.livable.service.PersonalInformationService;
import com.chuangshu.livable.service.UserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.persistence.Id;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/information")
public class PersonalInformationController {

    @Autowired
    private PersonalInformationService personalInformationService;

    @Autowired
    private IdInformationService idInformationService;

    @Autowired
    private UserService userService;

    @PostMapping("/personalInformation")
    @ApiOperation("更新个人信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "phone", dataType = "String", required = true, value = "电话"),
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

    @PostMapping("/headPortrait")
    @ApiOperation("更新头像")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "headPortrait", dataType = "String", required = true, value = "头像")
    })
    public ResultDTO headPortrait(String headPortrait) throws Exception{
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
        PersonalInformation personalInformation = new PersonalInformation();
        personalInformation.setHeadPortrait(headPortrait);
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

    @GetMapping("/getHeadPortrait")
    @ApiOperation("获取头像")
    public ResultDTO getHeadPortrait() throws Exception{
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        Integer userId = null;
        userId = Integer.parseInt(request.getSession().getAttribute("userID").toString());
        PersonalInformation personalInformation = new PersonalInformation();
        personalInformation.setUserId(userId);
        String headPortraitPath = null;
        if(personalInformationService.findByParams(personalInformation).size()!=0) {
            headPortraitPath = personalInformationService.findByParams(personalInformation).get(0).getHeadPortrait();
        }
        return ResultUtil.Success(headPortraitPath);
    }

    @GetMapping("/getPersonalInformation")
    @ApiOperation("获取个人信息")
    public ResultDTO getPersonalInformation() throws Exception{
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        Integer userId = null;
        userId = Integer.parseInt(request.getSession().getAttribute("userID").toString());
        PersonalInformation personalInformation = new PersonalInformation();
        personalInformation.setUserId(userId);
        PersonalInformation returnPersonalInformation = personalInformationService.findByParams(personalInformation).get(0);
        User user = userService.get(userId);
        PersonalInformationDTO personalInformationDTO = new PersonalInformationDTO(user,returnPersonalInformation);
        return ResultUtil.Success(personalInformationDTO);
    }


    @GetMapping("/getIDInformation")
    @ApiOperation("获取个人信息")
    public ResultDTO getIDInformation() throws Exception{
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        Integer userId = null;
        userId = Integer.parseInt(request.getSession().getAttribute("userID").toString());
        IdInformation idInformation = new IdInformation();
        idInformation.setUserId(userId);
        List<IdInformation> ids = idInformationService.findByParams(idInformation);
        if(ids.size() == 0){
            return ResultUtil.Error("500","没有这个人的资料");
        }
        IdInformation returnIdInformation = ids.get(0);
        return ResultUtil.Success(returnIdInformation);
    }
}
