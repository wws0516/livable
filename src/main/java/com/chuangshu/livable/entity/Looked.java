package com.chuangshu.livable.entity;

import javax.persistence.*;
import lombok.Data;

@Data
public class Looked {
    /**
     * 用户ID
     */
    @Id
    @Column(name = "user_id")
    private Integer userId;

    /**
     * 房源ID
     */
    @Id
    @Column(name = "hourse_id")
    private Integer hourseId;

    public static final String USER_ID = "userId";

    public static final String HOURSE_ID = "hourseId";
}