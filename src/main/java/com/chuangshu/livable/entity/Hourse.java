package com.chuangshu.livable.entity;

import javax.persistence.*;
import lombok.Data;

@Data
public class Hourse {
    /**
     * 房子ID
     */
    @Id
    @Column(name = "house_id")
    private Integer houseId;

    /**
     * 标题
     */
    private String title;

    /**
     * 地区
     */
    private String area;

    /**
     * 地址
     */
    private String address;

    /**
     * 房型
     */
    @Column(name = "hourse_type")
    private String hourseType;

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
     * 电梯有无
     */
    private String elevator;

    /**
     * 朝向
     */
    private String toward;

    /**
     * 车位有无
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
     * 特色
     */
    private String feature;

    /**
     * 面积
     */
    private String acreage;

    /**
     * 布局
     */
    private String layout;

    /**
     * 状态
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
     * 房屋配置
     */
    private String allocation;

    /**
     * 介绍
     */
    private String introduction;

    public static final String HOUSE_ID = "houseId";

    public static final String TITLE = "title";

    public static final String AREA = "area";

    public static final String ADDRESS = "address";

    public static final String HOURSE_TYPE = "hourseType";

    public static final String RENT = "rent";

    public static final String RENT_WAY = "rentWay";

    public static final String ELEVATOR = "elevator";

    public static final String TOWARD = "toward";

    public static final String CARPORT = "carport";

    public static final String ENERGY_CHARGE = "energyCharge";

    public static final String WATER_CHARGE = "waterCharge";

    public static final String FEATURE = "feature";

    public static final String ACREAGE = "acreage";

    public static final String LAYOUT = "layout";

    public static final String STATUS = "status";

    public static final String HOUSE_PROPRIETARY_CERTIFICATE = "houseProprietaryCertificate";

    public static final String PICTURE = "picture";

    public static final String ALLOCATION = "allocation";

    public static final String INTRODUCTION = "introduction";
}