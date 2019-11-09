package com.chuangshu.livable.redis;

import com.chuangshu.livable.entity.Allocation;
import com.chuangshu.livable.entity.Feature;
import com.chuangshu.livable.entity.House;
import com.chuangshu.livable.redis.entity.RedisBase;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;


/**
 * @auther zhang
 * @ TO DO
 * @creatTime 2019/11/7 15:24
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HouseRedisDTO extends RedisBase {

    //
    /**
     * 客厅个数
     */
    private Double numOfLivingRoom;

    /**
     * 房间个数
     */
    private Double numOfRoom;

    /**
     * 卫生间个数
     */
    private Double numOfToilet;

    /**
     * 租金
     */
    private Double rent;

    /**
     * 租房方式
     * 月付0，日付1
     */
    private Double rentType;

    /**
     * 租房人数
     */
    private Double numberOfPeople;

    /**
     * 方式
     * 整租0，合租1
     */
    private Double rentWay;

    /**
     * 电梯有无
     */
    private Double elevator;

    /**
     * 朝向
     */
    private Double toward;

    /**
     * 车位有无
     */
    private Double carport;

    /**
     * 电费
     * 免费0，收费1
     */
    private Double energyCharge;

    /**
     * 水费
     * 免费0，收费1
     */
    private Double waterCharge;




    /**
     * 面积
     */
    private Double acreage;

    //feature的属性

    /**
     * 独立卫浴
     */
    private Double independentBathroom;

    /**
     * 独立阳台
     */
    private Double independentBalcony;

    /**
     * 智能锁
     */
    private Double smartSock;

    /**
     * 可自行装修
     */
    private Double selfDecorating;

    /**
     * 首次出租
     */
    private Double firstRent;

    /**
     * 拎包入住
     */
    private Double fullyFurnished;

    /**
     * 地铁十分钟
     */
    private Double nearbySubway;

    /**
     * 随时看房
     */
    private Double anyTimeToSee;

    /**
     * 随时入住
     */
    private Double checkInAtOnce;



    //allcotaion属性
    /**
     * 电视
     */
    private Double tv;

    /**
     * 冰箱
     */
    private Double refrigerator;

    /**
     * 洗衣机
     */
    private Double washingMachine;

    /**
     * 空调
     */
    private Double airCondition;

    /**
     * WI-FI
     */
    private Double wifi;

    /**
     * 床
     */
    private Double beds;

    /**
     * 热水器
     */
    private Double waterHeater;

    /**
     * 衣柜
     */
    private Double chest;

    /**
     * 书桌
     */
    private Double desk;

    /**
     * 烹饪
     */
    private Double cooking;

    public HouseRedisDTO(House house, Feature feature, Allocation allocation) throws InvocationTargetException, IllegalAccessException {
        BeanUtils.copyProperties(this,feature);
        BeanUtils.copyProperties(this,allocation);
        BeanUtils.copyProperties(this,house);
        String houseType = house.getHouseType();
        String[] split = houseType.split("[室厅卫]");
        this.numOfRoom = new Double(split[0]);
        this.numOfLivingRoom = new Double(split[1]);
        this.numOfToilet = new Double(split[2]);
        if (house.getRentWay().equals("整租")) {
            this.rentWay = 0d;
        }else {
            this.rentWay = 1d;
        }
        if (house.getRentType().equals("月付")) {
            this.rentType = 0d;
        }else {
            this.rentType = 1d;
        }
        switch (house.getToward()){
            case "南":
                this.toward=1d;
                break;
            case "东南":
                this.toward = 0.875d;
                break;
            case "北":
                this.toward = 0.75d;
                break;
            case "东":
                this.toward = 0.625d;
                break;
            case "东北":
                this.toward = 0.5d;
                break;
            case "西南":
                this.toward = 0.375d;
                break;
            case "西":
                this.toward = 0.25d;
                break;
            case "西北":
                this.toward=0.125d;
                break;
            default:
                this.toward = 0d;
                break;
        }
    }

}

