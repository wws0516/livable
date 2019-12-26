package com.chuangshu.livable.entity;

import javax.persistence.*;
import lombok.Data;

@Data
@Table(name = "landlord_house_relation")
public class LandlordHouseRelation {
    public static final String RELATION_ID = "relationId";
    public static final String USER_ID = "userId";
    public static final String HOUSE_ID = "houseId";
    @Id
    @Column(name = "relation_id")
    private Integer relationId;
    /**
     * 房东ID
     */
    @Column(name = "user_id")
    private Integer userId;
    /**
     * 房子ID
     */
    @Column(name = "house_id")
    private Integer houseId;

    public LandlordHouseRelation(Integer houseId){
        this.houseId = houseId;
    }

    public LandlordHouseRelation(){}

}