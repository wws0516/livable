package com.chuangshu.livable.controller;

import com.chuangshu.livable.base.util.ResultUtil;
import com.chuangshu.livable.base.dto.ResultDTO;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: wws
 * @Date: 2019-07-13 12:23
 */

@RestController
public class BaseController {

    private RequestCache requestCache = new HttpSessionRequestCache();
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    /**
     * 当需要身份认证时，跳转到这里
     */
    @RequestMapping("/requireAuthentication")
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    public ResultDTO requireAuthentication(HttpServletRequest request, HttpServletResponse response){
        SavedRequest savedRequest = requestCache.getRequest(request, response);
        System.out.println(savedRequest);
        System.out.println("aa");
        if (savedRequest != null){
            String targetUrl = savedRequest.getRedirectUrl();
            if (StringUtils.endsWithIgnoreCase(targetUrl, ".html")){
                try {
                    redirectStrategy.sendRedirect(request, response, "/html/login.html");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return ResultUtil.Error(String.valueOf(HttpStatus.UNAUTHORIZED.value()), "您还未进行身份认证，请访问登陆页！");
    }


//    @RequestMapping("/findHome")
//    public String findHome(){
//        return "forward:/html/findHome.html";
//
//    }
}
