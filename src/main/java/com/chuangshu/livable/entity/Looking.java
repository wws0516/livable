package com.chuangshu.livable.entity;

import javax.persistence.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class Looking {
    /**
     * 预约id
     */
    @Column(name = "looking_id")
    @Id
    private Integer lookingId;

    /**
     * 用户ID
     */
    @Column(name = "user_id")
    private Integer userId;

    /**
     * 房源ID
     */
    @Column(name = "house_id")
    private Integer houseId;

    /**
     * 时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date data;

    /**
     * 地点
     */
    private String site;

    public static final String LOOKING_ID = "lookingId";

    public static final String USER_ID = "userId";

    public static final String HOUSE_ID = "houseId";

    public static final String DATA = "data";

    public static final String SITE = "site";
}