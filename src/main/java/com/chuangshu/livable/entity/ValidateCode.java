package com.chuangshu.livable.entity;

import lombok.Data;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;

/**
 * @Author: wws
 * @Date: 2019-07-06 20:34
 */
@Data
public class ValidateCode {

    private String code;

    private LocalDateTime expireTime;

    public ValidateCode(String code, Integer validTime) {
        this.code = code;
        this.expireTime = LocalDateTime.now().plusSeconds(validTime);
    }

    public boolean isExpired(){
        if (this.expireTime.isBefore(LocalDateTime.now()))
            return true;
        return false;
    }
}
