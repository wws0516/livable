package com.chuangshu.livable.entity;

import javax.persistence.*;
import lombok.Data;

@Data
@Table(name = "personal_information")
public class PersonalInformation {
    /**
     * 用户ID
     */
    @Id
    @Column(name = "user_id")
    private Integer userId;

    /**
     * 头像路径
     */
    @Column(name = "head_portrait")
    private String headPortrait;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 职业
     */
    private String job;

    /**
     * 爱好
     */
    private String hobby;

    /**
     * 电话
     */
    private String phone;

    public static final String USER_ID = "userId";

    public static final String HEAD_PORTRAIT = "headPortrait";

    public static final String AGE = "age";

    public static final String JOB = "job";

    public static final String HOBBY = "hobby";

    public static final String PHONE = "phone";
}