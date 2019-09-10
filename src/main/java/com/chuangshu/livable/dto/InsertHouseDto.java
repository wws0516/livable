package com.chuangshu.livable.dto;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;

@Data
public class InsertHouseDto {


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
    private String energyCharge;

    /**
     * 水费
     */
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
}
