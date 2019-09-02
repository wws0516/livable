package com.chuangshu.livable.security.validate.email;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: wws
 * @Date: 2019-07-16 17:37
 */
public class EmailCodeAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    public static final String CHUANG_SHU_FORM_EMAIL_KEY = "email";
    private String emailParameter = "username";
    private boolean postOnly = true;

    public EmailCodeAuthenticationFilter() {
        super(new AntPathRequestMatcher("/emailLogin", "POST"));
    }

    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if (this.postOnly && !request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        } else {
            String email = this.obtainEmail(request);
            if (email == null) {
                email = "";
            }

            email = email.trim();
            EmailCodeAuthenticationToken authRequest = new EmailCodeAuthenticationToken(email);
            this.setDetails(request, authRequest);
            return this.getAuthenticationManager().authenticate(authRequest);
        }
    }

    /**
     * 获取邮箱
     * @param request
     * @return
     */

    protected String obtainEmail(HttpServletRequest request) {
        return request.getParameter(this.emailParameter);
    }

    protected void setDetails(HttpServletRequest request, EmailCodeAuthenticationToken authRequest) {
        authRequest.setDetails(this.authenticationDetailsSource.buildDetails(request));
    }

    public void setEmailParameter(String emailParameter) {
        Assert.hasText(emailParameter, "Username parameter must not be empty or null");
        this.emailParameter = emailParameter;
    }

    public void setPostOnly(boolean postOnly) {
        this.postOnly = postOnly;
    }

    public final String getEmailParameter() {
        return this.emailParameter;
    }

}
