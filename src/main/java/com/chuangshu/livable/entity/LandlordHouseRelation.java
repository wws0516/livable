package com.chuangshu.livable.entity;

import java.util.Date;
import javax.persistence.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@Table(name = "landlord_house_relation")
public class LandlordHouseRelation {
    /**
     * 房东ID
     */
    @Id
    @Column(name = "user_id")
    private Integer userId;

    /**
     * 房子ID
     */
    @Column(name = "house_id")
    private Integer houseId;

    /**
     * 关系id
     */
    @Column(name = "relation_id")
    private Integer relationId;

    /**
     * 发行时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "publish_time")
    private Date publishTime;

    public static final String USER_ID = "userId";

    public static final String HOUSE_ID = "houseId";

    public static final String RELATION_ID = "relationId";

    public static final String PUBLISH_TIME = "publishTime";

    public LandlordHouseRelation() {
    }

    public LandlordHouseRelation(Integer houseId) {
        this.houseId = houseId;
    }
}