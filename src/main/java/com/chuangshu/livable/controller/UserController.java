package com.chuangshu.livable.controller;

import com.chuangshu.livable.base.ResultUtil;
import com.chuangshu.livable.base.dto.ResultDTO;
import com.chuangshu.livable.dto.InsertUserDTO;
import com.chuangshu.livable.dto.UpdateUserDTO;
import com.chuangshu.livable.entity.User;
import com.chuangshu.livable.service.UserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: wws
 * @Date: 2019-07-05 13:22
 */

@RequestMapping("/user")
@RestController
public class UserController {

    @Autowired
    UserService userService;
    @Autowired
    PasswordEncoder passwordEncoder;
    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    @PostMapping("/register")
    @ApiOperation("新增用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "name", dataType = "String", required = true, value = "姓名"),
            @ApiImplicitParam(paramType = "query", name = "gender", dataType = "String", required = true, value = "性别"),
            @ApiImplicitParam(paramType = "query", name = "email", dataType = "String", required = true, value = "邮箱"),
            @ApiImplicitParam(paramType = "query", name = "password", dataType = "String", required = true, value = "密码")
    })
    public ResultDTO<User> insertOneUser(InsertUserDTO insertUserDto){
        User user = new User(insertUserDto);
//        user.setUserId(UUID.randomUUID().toString());
        try {
            userService.save(user);
        }catch (Exception e){
            return ResultUtil.Error("500",e.getMessage());
        }
        return ResultUtil.Success();
    }


    @PostMapping("/update")
    @ApiOperation("修改用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "name", dataType = "String", required = true, value = "姓名"),
            @ApiImplicitParam(paramType = "query", name = "email", dataType = "String", required = true, value = "邮箱"),
            @ApiImplicitParam(paramType = "query", name = "gender", dataType = "String", required = true, value = "性别")
    })
    public ResultDTO<User> update(HttpServletRequest request, HttpServletResponse response, UpdateUserDTO updateUserDto){
        User user = new User(updateUserDto);
        user.setUserId(((User)sessionStrategy.getAttribute(new ServletWebRequest(request), "USER")).getUserId());
        try {
            userService.update(user);
        }catch (Exception e){
            return ResultUtil.Error("500",e.getMessage());
        }
        return ResultUtil.Success();
    }


}
