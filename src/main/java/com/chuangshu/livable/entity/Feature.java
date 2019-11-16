package com.chuangshu.livable.entity;

import javax.persistence.*;
import lombok.Data;

@Data
public class Feature {

    @Id
    @Column(name = "id")
    private Integer id;

    /**
     * 独立卫浴
     */
    @Column(name = "independent_bathroom")
    private Integer independentBathroom;

    /**
     * 独立阳台
     */
    @Column(name = "independent_balcony")
    private Integer independentBalcony;

    /**
     * 智能锁
     */
    @Column(name = "smart_sock")
    private Integer smartSock;

    /**
     * 可自行装修
     */
    @Column(name = "self_decorating")
    private Integer selfDecorating;

    /**
     * 首次出租
     */
    @Column(name = "first_rent")
    private Integer firstRent;

    /**
     * 拎包入住
     */
    @Column(name = "fully_furnished")
    private Integer fullyFurnished;

    /**
     * 地铁十分钟
     */
    @Column(name = "nearby_subway")
    private Integer nearbySubway;

    /**
     * 随时看房
     */
    @Column(name = "any_time_to_see")
    private Integer anyTimeToSee;

    /**
     * 随时入住
     */
    @Column(name = "check_in_at_once")
    private Integer checkInAtOnce;

    public static final String ID = "id";

    public static final String INDEPENDENT_BATHROOM = "independentBathroom";

    public static final String INDEPENDENT_BALCONY = "independentBalcony";

    public static final String SMART_SOCK = "smartSock";

    public static final String SELF_DECORATING = "selfDecorating";

    public static final String FIRST_RENT = "firstRent";

    public static final String FULLY_FURNISHED = "fullyFurnished";

    public static final String NEARBY_SUBWAY = "nearbySubway";

    public static final String ANY_TIME_TO_SEE = "anyTimeToSee";

    public static final String CHECK_IN_AT_ONCE = "checkInAtOnce";

    public Feature() {
    }

    public Feature(Integer independentBathroom, Integer independentBalcony, Integer smartSock, Integer selfDecorating, Integer firstRent, Integer fullyFurnished, Integer nearbySubway, Integer anyTimeToSee, Integer checkInAtOnce) {
        this.independentBathroom = independentBathroom;
        this.independentBalcony = independentBalcony;
        this.smartSock = smartSock;
        this.selfDecorating = selfDecorating;
        this.firstRent = firstRent;
        this.fullyFurnished = fullyFurnished;
        this.nearbySubway = nearbySubway;
        this.anyTimeToSee = anyTimeToSee;
        this.checkInAtOnce = checkInAtOnce;
    }
}