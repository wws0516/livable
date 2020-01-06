package com.chuangshu.livable.dto;

import com.chuangshu.livable.base.dto.BaseDTO;
import com.chuangshu.livable.entity.Allocation;
import com.chuangshu.livable.entity.Feature;
import com.chuangshu.livable.entity.House;
import com.chuangshu.livable.utils.esUtil.HouseIndexTemplate;
import lombok.Data;
import org.apache.commons.beanutils.BeanUtils;

import javax.persistence.Column;
import javax.persistence.Id;
import java.lang.reflect.InvocationTargetException;

/**
 * @author 叶三秋
 * @date 2019/12/25
 */
@Data
public class HouseDTO extends BaseDTO {
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
    private Allocation allocation;

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

    public HouseDTO() {}

    public HouseDTO(HouseIndexTemplate houseIndexTemplate) {
        this.houseId = houseIndexTemplate.getHouseId();
        this.title = houseIndexTemplate.getTitle();
        this.city = houseIndexTemplate.getCity();
        this.region = houseIndexTemplate.getRegion();
        this.address = houseIndexTemplate.getAddress();
        this.houseType = houseIndexTemplate.getHouseType();
        this.rent = String.valueOf(houseIndexTemplate.getRent());
        this.rentWay = houseIndexTemplate.getRentWay();
        this.rentType = houseIndexTemplate.getRentType();
        this.numberOfPeople = houseIndexTemplate.getNumberOfPeople();
        this.elevator = String.valueOf(houseIndexTemplate.getElevator());
        this.toward = houseIndexTemplate.getToward();
        this.carport = String.valueOf(houseIndexTemplate.getCarport());
        this.energyCharge = String.valueOf(houseIndexTemplate.getEnergyCharge());
        this.waterCharge = String.valueOf(houseIndexTemplate.getWaterCharge());
        this.feature = houseIndexTemplate.getFeature();
        this.acreage = String.valueOf(houseIndexTemplate.getAcreage());
        this.layout = houseIndexTemplate.getLayout();
        this.status = String.valueOf(houseIndexTemplate.getStatus());
        this.houseProprietaryCertificate = houseIndexTemplate.getHouseProprietaryCertificate();
        this.picture = houseIndexTemplate.getPicture();
        this.introduction = houseIndexTemplate.getIntroduction();
    }

    public HouseDTO(House house, Feature feature, Allocation allocation) throws InvocationTargetException, IllegalAccessException {
        BeanUtils.copyProperties(this,house);
        this.feature = feature;
        this.allocation = allocation;
    }
}
