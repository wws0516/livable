package com.chuangshu.livable.entity;

import javax.persistence.*;
import lombok.Data;

@Data
public class Allocation {

    @Id
    private Integer id;

    /**
     * 电视
     */
    private Integer tv;

    /**
     * 冰箱
     */
    private Integer refrigerator;

    /**
     * 洗衣机
     */
    @Column(name = "washing_machine")
    private Integer washingMachine;

    /**
     * 空调
     */
    @Column(name = "air-condition")
    private Integer airCondition;

    /**
     * WI-FI
     */
    private Integer wifi;

    /**
     * 床
     */
    private Integer beds;

    /**
     * 热水器
     */
    @Column(name = "water_heater")
    private Integer waterHeater;

    /**
     * 衣柜
     */
    private Integer chest;

    /**
     * 书桌
     */
    private Integer desk;

    /**
     * 烹饪
     */
    private Integer cooking;

    public static final String ID = "id";

    public static final String TV = "tv";

    public static final String REFRIGERATOR = "refrigerator";

    public static final String WASHING_MACHINE = "washingMachine";

    public static final String AIR_CONDITION = "airCondition";

    public static final String WIFI = "wifi";

    public static final String BEDS = "beds";

    public static final String WATER_HEATER = "waterHeater";

    public static final String CHEST = "chest";

    public static final String DESK = "desk";

    public static final String COOKING = "cooking";
}