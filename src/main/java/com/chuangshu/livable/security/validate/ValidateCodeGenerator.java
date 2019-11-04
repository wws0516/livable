package com.chuangshu.livable.security.validate;

import com.chuangshu.livable.utils.validateUtil.ValidateCode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * @Author: wws
 * @Date: 2019-07-16 12:31
 */
@Component
public interface ValidateCodeGenerator {

    ValidateCode generator(ServletWebRequest request);
}
