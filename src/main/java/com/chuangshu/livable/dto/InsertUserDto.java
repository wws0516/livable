package com.chuangshu.livable.dto;

import lombok.Data;

/**
 * @Author: wws
 * @Date: 2019-07-05 13:28
 */

@Data
public class InsertUserDto {

    /**
     * 姓名
     */
    private String name;

    /**
     * 性别
     */
    private String gender;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 密码
     */
    private String password;

}
