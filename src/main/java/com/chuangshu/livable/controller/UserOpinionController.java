package com.chuangshu.livable.controller;

import com.chuangshu.livable.base.ResultUtil;
import com.chuangshu.livable.base.dto.ResultDTO;
import com.chuangshu.livable.entity.UserOpinion;
import com.chuangshu.livable.service.UserOpinionService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;


/**
 * @author 叶三秋
 * @time 2019.09.10
 */
@RestController
@RequestMapping("/opinion")
public class UserOpinionController {

    @Autowired
    UserOpinionService userOpinionService;

    @PutMapping("/insertOneOpinion")
    @ApiOperation("新增意见")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "userId", dataType = "Integer", required = true, value = "用户ID"),
            @ApiImplicitParam(paramType = "query", name = "picture", dataType = "String", required = true, value = "图片URL"),
            @ApiImplicitParam(paramType = "query", name = "star", dataType = "Integer", required = true, value = "星数"),
            @ApiImplicitParam(paramType = "query", name = "opinion", dataType = "String", required = true, value = "意见"),
    })
    public ResultDTO<UserOpinion> insertOneOpinion(UserOpinion userOpinion)throws Exception{
        userOpinionService.save(userOpinion);
        return ResultUtil.Success(userOpinionService.findByParams(userOpinion));
    }

    @ApiOperation("得到所有opinion")
    @GetMapping("/getAllOpinion")
    public ResultDTO getAllOpinion(){
        List<UserOpinion> opinionList = null;
        try {
            opinionList = userOpinionService.findAll();
        }catch(Exception e){
            return ResultUtil.Error("500",e.getMessage());
        }
        return ResultUtil.Success(opinionList);
    }

    @GetMapping("/getOpinionByUserID")
    @ApiOperation("通过ID找到评论")
    @ApiImplicitParam(paramType = "query", name = "opinionID", dataType = "Integer", required = true, value = "意见ID")
    public ResultDTO getOpinionByOpinionID(Integer opinionID){
        UserOpinion userOpinion = null;
        try {
            userOpinion = userOpinionService.get(opinionID);
        }catch (Exception e){
            return ResultUtil.Error("500",e.getMessage());
        }
        return ResultUtil.Success(userOpinion);
    }
}