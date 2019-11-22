package com.chuangshu.livable.security;

import com.chuangshu.livable.entity.User;
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

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        User principal = (User) authentication.getPrincipal();
        httpServletRequest.getSession().setAttribute("userID", principal.getUserId());
        super.onAuthenticationSuccess(httpServletRequest, httpServletResponse, authentication);
    }
}
