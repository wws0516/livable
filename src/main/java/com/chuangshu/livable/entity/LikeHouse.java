package com.chuangshu.livable.entity;

import java.util.Date;
import javax.persistence.*;
import lombok.Data;

@Data
@Table(name = "like_house")
public class LikeHouse {
    @Id
    private Integer id;

    /**
     * 用户id
     */
    @Column(name = "user_id")
    private Integer userId;

    /**
     * 房源id
     */
    @Column(name = "house_id")
    private Integer houseId;

    private Date date;

    public static final String ID = "id";

    public static final String USER_ID = "userId";

    public static final String HOUSE_ID = "houseId";

    public static final String DATE = "date";
}