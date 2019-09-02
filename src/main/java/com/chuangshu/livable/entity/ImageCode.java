package com.chuangshu.livable.entity;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;

/**
 * @Author: wws
 * @Date: 2019-07-06 20:34
 */
public class ImageCode extends ValidateCode{

    private BufferedImage image;

    public ImageCode(BufferedImage image, String code, Integer validTime) {
        super(code, validTime);
        this.image = image;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }
}
