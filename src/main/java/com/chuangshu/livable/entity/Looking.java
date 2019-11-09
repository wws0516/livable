package com.chuangshu.livable.entity;

import java.util.Date;
import javax.persistence.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

@Data
public class Looking {
    public static final String LOOKING_ID = "lookingId";
    public static final String USER_ID = "userId";
    public static final String HOUSE_ID = "houseId";
    public static final String DATA = "data";
    @Id
    @Column(name = "looking_id")
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
     * 日期
     */
    @DateTimeFormat
    private Date data;
}