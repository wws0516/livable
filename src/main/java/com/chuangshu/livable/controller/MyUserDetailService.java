package com.chuangshu.livable.controller;

import com.chuangshu.livable.entity.User;
import com.chuangshu.livable.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @Author: wws
 * @Date: 2019-07-13 18:52
 */
@Component
public class MyUserDetailService implements UserDetailsService, SocialUserDetailsService {

    @Autowired
    UserService userService;

    @Override
    @RequestMapping("/login")
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        System.out.println(username);
//        User user = new User();
//        user.setName(username);
//        List<User> userList = new ArrayList<>();
//        try {
//            userList = userService.findByParams(user);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        System.out.println(userList.get(0));
        return new org.springframework.security.core.userdetails.User("1578494176@qq.com", "111", true, true, true, true, AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
    }

    @Override
    public SocialUserDetails loadUserByUserId(String userId) throws UsernameNotFoundException{
        return null;
    }
}
