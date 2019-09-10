package com.chuangshu.livable.controller;

import com.chuangshu.livable.base.ResultUtil;
import com.chuangshu.livable.base.dto.ResultDTO;
import com.chuangshu.livable.dto.InsertUserDto;
import com.chuangshu.livable.entity.User;
import com.chuangshu.livable.service.UserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @Author: wws
 * @Date: 2019-07-05 13:22
 */

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    @ApiOperation("新增用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "name", dataType = "String", required = true, value = "姓名"),
            @ApiImplicitParam(paramType = "query", name = "gender", dataType = "String", required = true, value = "性别"),
            @ApiImplicitParam(paramType = "query", name = "email", dataType = "String", required = true, value = "邮箱"),
            @ApiImplicitParam(paramType = "query", name = "password", dataType = "String", required = true, value = "密码")
    })
    public ResultDTO<User> insertOneUser(InsertUserDto insertUserDto){
        User user = new User(insertUserDto);
//        user.setUserId(UUID.randomUUID().toString());
        try {
            userService.save(user);
        }catch (Exception e){
            return ResultUtil.Error("500",e.getMessage());
        }
        return ResultUtil.Success();
    }


//    @PostMapping("/register")
//    @ApiOperation("用户登陆")
//    @ApiImplicitParams({
//            @ApiImplicitParam(paramType = "query", name = "accessname", dataType = "String", required = true, value = "姓名"),
//            @ApiImplicitParam(paramType = "query", name = "code", dataType = "String", required = true, value = "邮箱"),
//            @ApiImplicitParam(paramType = "query", name = "loginType", dataType = "Integer", required = true, value = "登陆方式")
//    })
//    public ResultDTO<User> login(InsertUserDto insertUserDto){
//        User user = new User(insertUserDto);
//        user.setUserId(UUID.randomUUID().toString());
//        try {
//            userService.save(user);
//        }catch (Exception e){
//            return ResultUtil.Error("500",e.getMessage());
//        }
//        return ResultUtil.Success();
//    }


}
