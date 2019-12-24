package com.chuangshu.livable.service.rbac.impl;

import com.chuangshu.livable.entity.User;
import com.chuangshu.livable.entity.UserRole;
import com.chuangshu.livable.service.RoleService;
import com.chuangshu.livable.service.UserRoleService;
import com.chuangshu.livable.service.rbac.RbacService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: wws
 * @Date: 2019-12-23 10:57
 */
@Component("rbacService")
public class RbacServiceImpl implements RbacService {

    @Autowired
    UserRoleService userRoleService;

    @Autowired
    RoleService roleService;

    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public boolean hasPermission(HttpServletRequest request, Authentication authentication) {
        Object user = authentication.getPrincipal();
        UserRole userRole = new UserRole();
        if (user instanceof User){
            userRole.setUserId(((User) user).getUserId());
        }
        List<String> roles = new ArrayList<String>();
        try {
            List<UserRole> userRoles = userRoleService.findByParams(userRole);
            for (UserRole ur : userRoles) {
                roles.add(roleService.get(ur.getRoleId()).getName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (antPathMatcher.match("/checkLandlordFailure", request.getRequestURI()) && !roles.contains("ADMIN"))
            return false;
        if (antPathMatcher.match("/checkLandlordSuccess", request.getRequestURI()) && !roles.contains("ADMIN"))
            return false;
        if (antPathMatcher.match("/house/insert", request.getRequestURI()) && !roles.contains("LANDLORD"))
            return false;
        if (antPathMatcher.match("/house/updateHouseByDto", request.getRequestURI()) && !roles.contains("LANDLORD"))
            return false;
        if (antPathMatcher.match("/house/deleteHouse", request.getRequestURI()) && !roles.contains("LANDLORD"))
            return false;

        return true;

    }
}
