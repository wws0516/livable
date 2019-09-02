package com.chuangshu.livable.security.validate;


import org.springframework.security.core.AuthenticationException;

/**
 * @Author: wws
 * @Date: 2019-07-15 21:23
 */
public class ValidateCodeException extends AuthenticationException {

    /**
     * 验证码异常
     * @param msg
     */
    public ValidateCodeException(String msg) {
        super(msg);
    }
}
