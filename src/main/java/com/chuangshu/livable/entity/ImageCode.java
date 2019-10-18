package com.chuangshu.livable.entity;

import lombok.Data;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;

/**
 * @Author: wws
 * @Date: 2019-07-06 20:34
 */
@Data
public class ImageCode extends ValidateCode{

    private BufferedImage image;

    public ImageCode(BufferedImage image, String code, Integer validTime) {
        super(code, validTime);
        this.image = image;
    }
}
