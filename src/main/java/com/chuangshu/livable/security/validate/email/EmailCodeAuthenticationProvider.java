package com.chuangshu.livable.security.validate.email;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @Author: wws
 * @Date: 2019-07-16 20:04
 */
public class EmailCodeAuthenticationProvider implements AuthenticationProvider {

    private UserDetailsService userDetailsService;

    public UserDetailsService getUserDetailsService() {
        return userDetailsService;
    }

    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        EmailCodeAuthenticationToken emailCodeAuthenticationToken = (EmailCodeAuthenticationToken)authentication;
        UserDetails user = (UserDetails) userDetailsService.loadUserByUsername((String) emailCodeAuthenticationToken.getPrincipal());
        if (user == null){
            throw new InternalAuthenticationServiceException("无法获取用户信息");
        }
        EmailCodeAuthenticationToken emailCodeAuthenticationTokenResult = new EmailCodeAuthenticationToken(user, user.getAuthorities());
        emailCodeAuthenticationTokenResult.setDetails(emailCodeAuthenticationToken.getDetails());
        return emailCodeAuthenticationTokenResult;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return EmailCodeAuthenticationToken.class.isAssignableFrom(aClass);
    }
}
