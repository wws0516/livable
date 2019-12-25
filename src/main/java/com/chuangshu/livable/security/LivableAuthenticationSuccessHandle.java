package com.chuangshu.livable.security;

import com.chuangshu.livable.base.util.ResultUtil;
import com.chuangshu.livable.entity.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: wws
 * @Date: 2019-07-13 16:36
 */
@Component("livableAuthenticationSuccessHandle")
public class LivableAuthenticationSuccessHandle extends SavedRequestAwareAuthenticationSuccessHandler {

    @Autowired
    ObjectMapper objectMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        httpServletRequest.getSession().getAttributeNames();
        User principal = (User) authentication.getPrincipal();
        httpServletRequest.getSession().setAttribute("userID", principal.getUserId());
        httpServletResponse.setContentType("application/json;charset=UTF-8");
        httpServletResponse.getWriter().write(objectMapper.writeValueAsString(ResultUtil.Success(authentication)));//将java对象转成json字符串写入response，Authtication参数中包含我们的认证信息

//        super.onAuthenticationSuccess(httpServletRequest, httpServletResponse, authentication);
    }
}
