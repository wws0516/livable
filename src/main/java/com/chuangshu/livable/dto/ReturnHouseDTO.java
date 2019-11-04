package com.chuangshu.livable.dto;

import com.chuangshu.livable.entity.Allocation;
import com.chuangshu.livable.entity.Feature;
import com.chuangshu.livable.entity.House;
import lombok.Data;

@Data
public class ReturnHouseDTO {

    /**
     * 房源ID
     */
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
    private String houseType;

    /**
     * 租金
     */
    private String rent;

    /**
     * 方式
     */
    private String rentWay;

    /**
     * 租房方式
     */
    private String rentType;

    /**
     * 租房人数
     */
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
    private String energyCharge;

    /**
     * 水费
     */
    private String waterCharge;

    /**
     * 特色id
     */
    /**
     * 独立卫浴
     */
    private Integer independentBathroom;

    /**
     * 独立阳台
     */
    private Integer independentBalcony;

    /**
     * 智能锁
     */
    private Integer smartSock;

    /**
     * 可自行装修
     */
    private Integer selfDecorating;

    /**
     * 首次出租
     */
    private Integer firstRent;

    /**
     * 拎包入住
     */
    private Integer fullyFurnished;

    /**
     * 地铁十分钟
     */
    private Integer nearbySubway;

    /**
     * 随时看房
     */
    private Integer anyTimeToSee;

    /**
     * 随时入住
     */
    private Integer checkInAtOnce;

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
    private Integer washingMachine;

    /**
     * 空调
     */
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

    /**
     * 电梯有无
     */
    private String status;

    /**
     * 房产证
     */
    private String houseProprietaryCertificate;

    /**
     * 图片
     */
    private String picture;

    /**
     * 介绍
     */
    private String introduction;

    public ReturnHouseDTO(House house, Feature feature, Allocation allocation){
        this.title = house.getTitle();
        this.city = house.getCity();
        this.region = house.getRegion();
        this.address = house.getAddress();
        this.houseType = house.getHouseType();
        this.rent = house.getRent();
        this.rentType = house.getRentType();
        this.rentWay = house.getRentWay();
        this.numberOfPeople = house.getNumberOfPeople();
        this.elevator = house.getElevator();
        this.toward = house.getToward();
        this.carport = house.getCarport();
        this.energyCharge = house.getEnergyCharge();
        this.waterCharge = house.getWaterCharge();
        this.independentBathroom = feature.getIndependentBathroom();
        this.independentBalcony = feature.getIndependentBalcony();
        this.smartSock = feature.getSmartSock();
        this.selfDecorating = feature.getSelfDecorating();
        this.firstRent = feature.getFirstRent();
        this.fullyFurnished = feature.getFullyFurnished();
        this.nearbySubway = feature.getNearbySubway();
        this.anyTimeToSee = feature.getAnyTimeToSee();
        this.checkInAtOnce = feature.getCheckInAtOnce();
        this.acreage = house.getAcreage();
        this.layout = house.getLayout();
        this.tv = allocation.getTv();
        this.refrigerator = allocation.getRefrigerator();
        this.washingMachine = allocation.getWashingMachine();
        this.airCondition = allocation.getAirCondition();
        this.wifi = allocation.getWifi();
        this.beds = allocation.getBeds();
        this.waterHeater = allocation.getWaterHeater();
        this.chest = allocation.getChest();
        this.desk = allocation.getDesk();
        this.cooking = allocation.getCooking();
        this.status = house.getStatus();
        this.houseProprietaryCertificate = house.getHouseProprietaryCertificate();
        this.picture = house.getPicture();
        this.introduction = house.getIntroduction();
    }
}
