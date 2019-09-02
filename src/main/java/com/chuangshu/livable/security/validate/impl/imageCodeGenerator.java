package com.chuangshu.livable.security.validate.impl;

import com.chuangshu.livable.entity.ImageCode;
import com.chuangshu.livable.security.validate.ValidateCodeGenerator;
import com.chuangshu.livable.entity.ValidateCode;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.context.request.ServletWebRequest;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

/**
 * @Author: wws
 * @Date: 2019-07-16 13:43
 */
@Component
public class imageCodeGenerator implements ValidateCodeGenerator {
    @Override
    public ValidateCode generator(ServletWebRequest request) {

            //生成图片验证码
            int width = 65;
            int height = 25;
            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics graphics = image.getGraphics();
            Random random = new Random();
            graphics.setColor(getRandColor(200, 250));
            graphics.fillRect(0, 0, width, height);
            graphics.setFont(new Font("Times New Roman", Font.ITALIC, 20));
            graphics.setColor(getRandColor(160, 200));
            for (int i = 0; i < 155; i++){
                int x = random.nextInt(width);
                int y = random.nextInt(height);
                int xl = random.nextInt(12);
                int yl = random.nextInt(12);
                graphics.drawLine(x, y, x+xl, y+yl);
            }
            String sRand = "";
            for (int i = 0; i < 4; i++){
                String rand = String.valueOf(random.nextInt(10));
                sRand += rand;
                graphics.setColor(new Color(20+random.nextInt(110),20+random.nextInt(110),20+random.nextInt(110)));
                graphics.drawString(rand, 13*i+6, 16);
            }
            graphics.dispose();

            return new ImageCode(image, sRand, 60);

        }

        /**
         * 生成随机背景条纹
         *
         */
        private Color getRandColor(int fc, int bc){
            Random random = new Random();
            if (fc > 255){
                fc = 255;
            }
            if (bc > 255){
                bc = 255;
            }
            int r = fc + random.nextInt(bc - fc);
            int g = fc + random.nextInt(bc - fc);
            int b = fc + random.nextInt(bc - fc);
            return new Color(r, g, b);
        }

}
