package com.chuangshu.livable.entity;

import javax.persistence.*;
import lombok.Data;


@Data
@Table(name = "landlord_hourse_relation")
public class LandlordHourseRelation {
    /**
     * 房东ID
     */
    @Id
    @Column(name = "user_id")
    private Integer userId;

    /**
     * 房子ID
     */
    @Id
    @Column(name = "hourse_id")
    private Integer hourseId;

    public static final String USER_ID = "userId";

    public static final String HOURSE_ID = "hourseId";
}