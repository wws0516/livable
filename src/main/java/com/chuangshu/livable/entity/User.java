package com.chuangshu.livable.entity;

import javax.persistence.*;
import lombok.Data;

@Data
public class User {
    /**
     * 用户ID
     */
    @Column(name = "user_id")
    private Integer userId;

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

    public static final String USER_ID = "userId";

    public static final String NAME = "name";

    public static final String GENDER = "gender";

    public static final String EMAIL = "email";

    public static final String PASSWORD = "password";
}