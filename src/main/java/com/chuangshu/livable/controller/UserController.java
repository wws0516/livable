package com.chuangshu.livable.controller;

import com.chuangshu.livable.base.util.ResultUtil;
import com.chuangshu.livable.base.dto.ResultDTO;
import com.chuangshu.livable.dto.InsertUserDTO;
import com.chuangshu.livable.dto.UpdateUserDTO;
import com.chuangshu.livable.entity.PersonalInformation;
import com.chuangshu.livable.entity.User;
import com.chuangshu.livable.entity.UserRole;
import com.chuangshu.livable.service.PersonalInformationService;
import com.chuangshu.livable.service.UserRoleService;
import com.chuangshu.livable.service.UserService;
import com.chuangshu.livable.service.redis.UserRedisService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.social.security.SocialUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: wws
 * @Date: 2019-07-05 13:22
 */

@RequestMapping("/user")
@RestController
public class UserController implements UserDetailsService {

    @Autowired
    UserService userService;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    UserRoleService userRoleService;
    @Autowired
    UserRedisService userRedisService;
    @Autowired
    PersonalInformationService personalInformationService;

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
            userRoleService.save(new UserRole(user.getUserId(), 2));
            userRedisService.setNewUser(user);

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

        @Override
        @PostMapping("/login")
        @ApiOperation("获取用户信息")
        @ApiImplicitParams({
                @ApiImplicitParam(paramType = "query", name = "username", dataType = "String", required = true, value = "用户名"),
                @ApiImplicitParam(paramType = "query", name = "password", dataType = "String", required = true, value = "密码"),
        })
        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

            User user = new User();
            if (username.endsWith(".com"))
                user.setEmail(username);
            else
                user.setName(username);
            List<User> list = new ArrayList<>();
            try {
                list = userService.findByParams(user);
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (list.size()==0){
                return null;
            }
            User user1 = list.get(0);
            return user1;
        }

    @GetMapping("getRoommate")
    @ApiOperation("获取推荐舍友")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "userId", dataType = "int", required = true, value = "用户id"),
            @ApiImplicitParam(paramType = "query", name = "houseId", dataType = "int", required = true, value = "房源id"),
    })
    public ResultDTO<User> getRoommate(Integer userId, Integer houseId) throws Exception {
        List<Integer> userIdList = new ArrayList<>();
        try {
            userIdList = userRedisService.userGetRoomate(userId, houseId);
        }catch (Exception e){
            return ResultUtil.Error("500",e.getMessage());
        }
        ArrayList<PersonalInformation> informationList = new ArrayList<>();
        for (Integer id : userIdList) {
            informationList.add(personalInformationService.get(id));
        }
        return ResultUtil.Success(informationList);

    }
}
