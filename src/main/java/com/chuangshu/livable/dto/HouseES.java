package com.chuangshu.livable.dto;

import com.chuangshu.livable.entity.House;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "livable", type = "house", shards = 1, replicas = 0)
@Data
public class HouseES {

    @Id
    private Integer houseId;
//    @Field(type = FieldType.Text,analyzer = "ik_max_word")
    private String house_title;
    private String area;
    private String address;
    private String houseType;
    private String rent;
    private String rentWay;
    private String elevator;
    private String toward;
    private String carport;
    private String energyCharge;
    private String waterCharge;
    private String feature;
    private String acreage;
    private String layout;
    private String allocation;
    private String introduction;
    private String status;
    private String houseProprietaryCertificate;
    private String picture;

    public HouseES(House house){
        this.houseId = house.getHouseId();
        this.house_title = house.getTitle();
        this.area = house.getArea();
        this.address = house.getAddress();
        this.houseType = house.getHouseType();
        this.rent = house.getRent();
        this.rentWay = house.getRentWay();
        this.elevator = house.getElevator();
        this.toward = house.getToward();
        this.carport = house.getCarport();
        this.energyCharge = house.getEnergyCharge();
        this.waterCharge = house.getWaterCharge();
        this.feature = house.getFeature();
        this.acreage = house.getAcreage();
        this.layout = house.getLayout();
        this.allocation = house.getAllocation();
        this.introduction = house.getIntroduction();
        this.status = house.getStatus();
        this.houseProprietaryCertificate = house.getHouseProprietaryCertificate();
        this.picture = house.getPicture();
    }


    public HouseES(){

    }
}
