package com.chuangshu.livable.entity;

import javax.persistence.*;
import lombok.Data;

@Data
@Table(name = "user_opinion")
public class UserOpinion {
    /**
     * 意见ID
     */
    @Column(name = "opinion_id")
    private Integer opinionId;

    /**
     * 用户ID
     */
    @Column(name = "user_id")
    private Integer userId;

    /**
     * 图片
     */
    private String picture;

    /**
     * 星数
     */
    private Integer star;

    /**
     * 意见
     */
    private String opinion;

    public static final String OPINION_ID = "opinionId";

    public static final String USER_ID = "userId";

    public static final String PICTURE = "picture";

    public static final String STAR = "star";

    public static final String OPINION = "opinion";
}