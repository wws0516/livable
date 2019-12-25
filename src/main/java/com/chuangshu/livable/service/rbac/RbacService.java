package com.chuangshu.livable.service.rbac;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

/**
 * @Author: wws
 * @Date: 2019-12-23 10:53
 */
@Component
public interface RbacService {

    boolean hasPermission(HttpServletRequest request, Authentication authentication);
}
