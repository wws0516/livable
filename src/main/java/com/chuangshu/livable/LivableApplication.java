package com.chuangshu.livable;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import tk.mybatis.spring.annotation.MapperScan;

@EnableSwagger2
@SpringBootApplication
@MapperScan("com.chuangshu.livable.mapper")
public class LivableApplication {

    public static void main(String[] args) {
        SpringApplication.run(LivableApplication.class, args);
    }

}
