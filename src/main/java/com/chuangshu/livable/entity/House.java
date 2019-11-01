package com.chuangshu.livable.entity;

import javax.persistence.*;
import lombok.Data;

@Data
public class House {
    /**
     * 房源ID
     */
    @Column(name = "house_id")
    private Integer houseId;

    /**
     * 标题
     */
    private String title;

    /**
     * 城市
     */
    private String city;

    /**
     * 地区
     */
    private String region;

    /**
     * 地址
     */
    private String address;

    /**
     * 房型
     */
    @Column(name = "house_type")
    private String houseType;

    /**
     * 租金
     */
    private String rent;

    /**
     * 方式
     */
    @Column(name = "rent_way")
    private String rentWay;

    /**
     * 租房方式
     */
    @Column(name = "rent_type")
    private String rentType;

    /**
     * 租房人数
     */
    @Column(name = "number_of_people")
    private Integer numberOfPeople;

    /**
     * 电梯有无
     */
    private String elevator;

    /**
     * 电梯有无
     */
    private String toward;

    /**
     * 电梯有无
     */
    private String carport;

    /**
     * 电费
     */
    @Column(name = "energy_charge")
    private String energyCharge;

    /**
     * 水费
     */
    @Column(name = "water_charge")
    private String waterCharge;

    /**
     * 特色id
     */
    @Column(name = "feature_id")
    private Integer featureId;

    /**
     * 面积
     */
    private String acreage;

    /**
     * 布局
     */
    private String layout;

    /**
     * 配置id
     */
    @Column(name = "allocation_id")
    private Integer allocationId;

    /**
     * 电梯有无
     */
    private String status;

    /**
     * 房产证
     */
    @Column(name = "house_proprietary_certificate")
    private String houseProprietaryCertificate;

    /**
     * 图片
     */
    private String picture;

    /**
     * 介绍
     */
    private String introduction;

    public static final String HOUSE_ID = "houseId";

    public static final String TITLE = "title";

    public static final String CITY = "city";

    public static final String REGION = "region";

    public static final String ADDRESS = "address";

    public static final String HOUSE_TYPE = "houseType";

    public static final String RENT = "rent";

    public static final String RENT_WAY = "rentWay";

    public static final String RENT_TYPE = "rentType";

    public static final String NUMBER_OF_PEOPLE = "numberOfPeople";

    public static final String ELEVATOR = "elevator";

    public static final String TOWARD = "toward";

    public static final String CARPORT = "carport";

    public static final String ENERGY_CHARGE = "energyCharge";

    public static final String WATER_CHARGE = "waterCharge";

    public static final String FEATURE_ID = "featureId";

    public static final String ACREAGE = "acreage";

    public static final String LAYOUT = "layout";

    public static final String ALLOCATION_ID = "allocationId";

    public static final String STATUS = "status";

    public static final String HOUSE_PROPRIETARY_CERTIFICATE = "houseProprietaryCertificate";

    public static final String PICTURE = "picture";

    public static final String INTRODUCTION = "introduction";
}