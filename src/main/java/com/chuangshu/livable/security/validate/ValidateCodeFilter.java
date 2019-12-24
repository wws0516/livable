package com.chuangshu.livable.security.validate;

import com.chuangshu.livable.controller.ValidateCodeController;
import com.chuangshu.livable.utils.validateUtil.ValidateCode;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: wws
 * @Date: 2019-07-15 19:56
 */
public class ValidateCodeFilter extends OncePerRequestFilter {

    private AuthenticationFailureHandler authenticationFailureHandler;

    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    public AuthenticationFailureHandler getAuthenticationFailureHandler() {
        return authenticationFailureHandler;
    }

    public void setAuthenticationFailureHandler(AuthenticationFailureHandler authenticationFailureHandler) {
        this.authenticationFailureHandler = authenticationFailureHandler;
    }

    public SessionStrategy getSessionStrategy() {
        return sessionStrategy;
    }

    public void setSessionStrategy(SessionStrategy sessionStrategy) {
        this.sessionStrategy = sessionStrategy;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        if (StringUtils.equals("/login", httpServletRequest.getRequestURI()) && StringUtils.equalsIgnoreCase(httpServletRequest.getMethod(), "post")){
            try {
                validate(new ServletWebRequest(httpServletRequest), "IMAGE");
            } catch (ValidateCodeException e) {
                authenticationFailureHandler.onAuthenticationFailure(httpServletRequest, httpServletResponse, e);
                return;
            }
        }
        if (StringUtils.equals("/emailLogin", httpServletRequest.getRequestURI()) && StringUtils.equalsIgnoreCase(httpServletRequest.getMethod(), "post")){
            try {
                validate(new ServletWebRequest(httpServletRequest), "EMAIL");
            } catch (ValidateCodeException e) {
                authenticationFailureHandler.onAuthenticationFailure(httpServletRequest, httpServletResponse, e);
                return;
            }
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    private void validate(ServletWebRequest servletWebRequest, String loginType) {
        ValidateCode codeInSession = (ValidateCode) sessionStrategy.getAttribute(servletWebRequest, ValidateCodeController.SESSION_KEY+loginType);
        String codeInRequest = null;
        try {
            if (loginType.equals("IMAGE"))
                codeInRequest = ServletRequestUtils.getStringParameter(servletWebRequest.getRequest(), "imageCode");
            else                 codeInRequest = ServletRequestUtils.getStringParameter(servletWebRequest.getRequest(), "emailCode");
        } catch (ServletRequestBindingException e) {
            e.printStackTrace();
        }

            if (codeInSession == null) {
                throw new ValidateCodeException("验证码不存在");
            }
            if (codeInSession.isExpired()) {
                sessionStrategy.removeAttribute(servletWebRequest, ValidateCodeController.SESSION_KEY+loginType);
                throw new ValidateCodeException("验证码已过期");
            }
            System.out.println(codeInRequest);
            if (!codeInRequest.equals(codeInSession.getCode())) {
                throw new ValidateCodeException("验证码不匹配");
            }
            sessionStrategy.removeAttribute(servletWebRequest, ValidateCodeController.SESSION_KEY+loginType);

    }
}
