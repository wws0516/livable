package com.chuangshu.livable.utils.validateUtil;

import lombok.Data;

import java.awt.image.BufferedImage;

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
