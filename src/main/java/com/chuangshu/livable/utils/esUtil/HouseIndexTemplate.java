package com.chuangshu.livable.utils.esUtil;

import com.chuangshu.livable.entity.Feature;
import com.chuangshu.livable.utils.baiduMapUtil.BaiduMapLocation;
import lombok.Data;

import java.util.List;

/**
 * @Author: wws
 * @Date: 2019-09-23 08:30
 */
@Data
public class HouseIndexTemplate {

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
    private Feature feature;

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
    private Integer allocationId;

    /**
     * 状态
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

    /**
     * 自动提示suggest
     *
     */
    private List<HouseSuggest> suggests;

}
