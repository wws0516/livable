package com.chuangshu.livable.entity;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;

/**
 * @Author: wws
 * @Date: 2019-07-06 20:34
 */
public class ValidateCode {

    private String code;

    private LocalDateTime expireTime;

    public ValidateCode(String code, Integer validTime) {
        this.code = code;
        this.expireTime = LocalDateTime.now().plusSeconds(validTime);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LocalDateTime getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(LocalDateTime expireTime) {
        this.expireTime = expireTime;
    }

    public boolean isExpried() {
        return expireTime.isBefore(LocalDateTime.now());
    }
}
