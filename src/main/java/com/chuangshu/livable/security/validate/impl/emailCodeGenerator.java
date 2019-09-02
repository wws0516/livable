package com.chuangshu.livable.security.validate.impl;

import com.chuangshu.livable.entity.EmailCode;
import com.chuangshu.livable.entity.ValidateCode;
import com.chuangshu.livable.security.validate.ValidateCodeGenerator;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * @Author: wws
 * @Date: 2019-07-16 13:50
 */
@Component
public class emailCodeGenerator implements ValidateCodeGenerator {

    @Override
    public ValidateCode generator(ServletWebRequest request) {
        String code = RandomStringUtils.randomNumeric(6);
        return new EmailCode(code, 60);
    }
}
