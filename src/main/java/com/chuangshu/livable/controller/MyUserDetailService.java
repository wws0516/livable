package com.chuangshu.livable.controller;

import com.chuangshu.livable.dto.UserDTO;
import com.chuangshu.livable.entity.User;
import com.chuangshu.livable.service.UserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @Author: wws
 * @Date: 2019-07-13 18:52
 */
@Component
@RestController
public class MyUserDetailService implements UserDetailsService, SocialUserDetailsService {

    @Autowired
    UserService userService;

    @Override
    @PostMapping("/login")
    @ApiOperation("用户登陆")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "name", dataType = "String", required = true, value = "用户名"),
            @ApiImplicitParam(paramType = "query", name = "password", dataType = "String", required = true, value = "密码"),
    })
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = new User();
        user.setName(username);
        List<User> list = new ArrayList<>();
        try {
            list = userService.findByParams(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list.get(0);
//        return new org.springframework.security.core.userdetails.User("1578494176@qq.com", "111", true, true, true, true, AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));

    }

    @Override
    public SocialUserDetails loadUserByUserId(String userId) throws UsernameNotFoundException{
        return null;
    }
}
