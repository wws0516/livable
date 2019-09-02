package com.chuangshu.livable.security.validate.email;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import javax.security.auth.Subject;
import java.util.Collection;

/**
 * @Author: wws
 * @Date: 2019-07-16 17:23
 */
public class EmailCodeAuthenticationToken extends AbstractAuthenticationToken {

    private static final long serialVersionUID = 510L;
    private final Object principal;

    public EmailCodeAuthenticationToken(String email) {
        super(null);
        this.principal = email;
        this.setAuthenticated(false);
    }

    public EmailCodeAuthenticationToken(Object principal, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        super.setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    public Object getPrincipal() {
        return this.principal;
    }

    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        if (isAuthenticated) {
            throw new IllegalArgumentException("Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        } else {
            super.setAuthenticated(false);
        }
    }

    public void eraseCredentials() {
        super.eraseCredentials();
    }

    @Override
    public boolean implies(Subject subject) {
        return false;
    }
}
