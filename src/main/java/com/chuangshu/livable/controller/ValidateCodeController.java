package com.chuangshu.livable.controller;

import com.chuangshu.livable.utils.validateUtil.ImageCode;
import com.chuangshu.livable.utils.validateUtil.EmailCode;
import com.chuangshu.livable.security.validate.EmailCodeSender;
import com.chuangshu.livable.security.validate.ValidateCodeGenerator;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.imageio.ImageIO;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: wws
 * @Date: 2019-07-06 21:23
 */

@RestController
public class ValidateCodeController {

    public static final String SESSION_KEY = "SESSION_KEY_CODE_";

    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    @Autowired
    private ValidateCodeGenerator imageCodeGenerator;

    @Autowired
    private ValidateCodeGenerator emailCodeGenerator;

    @Autowired
    private EmailCodeSender sender;

    @GetMapping("/imageCode")
    @ApiOperation("生成图形验证码")
    public void createImageCode(HttpServletRequest request, HttpServletResponse response){

        ImageCode imageCode = (ImageCode) imageCodeGenerator.generator(new ServletWebRequest(request));
        sessionStrategy.setAttribute(new ServletWebRequest(request), SESSION_KEY+"IMAGE", imageCode);
        try {
            ImageIO.write(imageCode.getImage(), "JPEG", response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/emailCode")
    @ApiOperation("发送邮箱验证码")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "email", dataType = "String", required = true, value = "邮箱"),
    })
    public void createSmsCode(HttpServletRequest request, HttpServletResponse response, String email) {
        EmailCode emailCode = (EmailCode) emailCodeGenerator.generator(new ServletWebRequest(request));
        System.out.println(emailCode.getCode());
        sessionStrategy.setAttribute(new ServletWebRequest(request), SESSION_KEY+"EMAIL", emailCode);
        try {
            sender.send(emailCode.getCode(), email);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
