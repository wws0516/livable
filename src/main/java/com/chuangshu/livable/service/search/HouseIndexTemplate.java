package com.chuangshu.livable.service.search;

import com.chuangshu.livable.entity.BaiduMapLocation;

/**
 * @Author: wws
 * @Date: 2019-09-23 08:30
 */
public class HouseIndexTemplate {

    private Integer houseId;
    private String title;
    private String city;
    private String region;
    private String address;
    private String hourse_type;
    private String rent;
    private String rent_way;
    private char elevator;
    private char toward;
    private char carport;
    private String energy_charge;
    private String water_charge;
    private String feature;
    private String acreage;
    private String layout;
    private String allocation;
    private String introduction;
    private Integer status;
    private String house_proprietary_certificate;
    private String picture;
    private BaiduMapLocation baiduMapLocation;

    public BaiduMapLocation getBaiduMapLocation() {
        return baiduMapLocation;
    }

    public void setBaiduMapLocation(BaiduMapLocation baiduMapLocation) {
        this.baiduMapLocation = baiduMapLocation;
    }

    public Integer getHouseId() {
        return houseId;
    }

    public void setHouseId(Integer houseId) {
        this.houseId = houseId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getHourse_type() {
        return hourse_type;
    }

    public void setHourse_type(String hourse_type) {
        this.hourse_type = hourse_type;
    }

    public String getRent() {
        return rent;
    }

    public void setRent(String rent) {
        this.rent = rent;
    }

    public String getRent_way() {
        return rent_way;
    }

    public void setRent_way(String rent_way) {
        this.rent_way = rent_way;
    }

    public char getElevator() {
        return elevator;
    }

    public void setElevator(char elevator) {
        this.elevator = elevator;
    }

    public char getToward() {
        return toward;
    }

    public void setToward(char toward) {
        this.toward = toward;
    }

    public char getCarport() {
        return carport;
    }

    public void setCarport(char carport) {
        this.carport = carport;
    }

    public String getEnergy_charge() {
        return energy_charge;
    }

    public void setEnergy_charge(String energy_charge) {
        this.energy_charge = energy_charge;
    }

    public String getWater_charge() {
        return water_charge;
    }

    public void setWater_charge(String water_charge) {
        this.water_charge = water_charge;
    }

    public String getFeature() {
        return feature;
    }

    public void setFeature(String feature) {
        this.feature = feature;
    }

    public String getAcreage() {
        return acreage;
    }

    public void setAcreage(String acreage) {
        this.acreage = acreage;
    }

    public String getLayout() {
        return layout;
    }

    public void setLayout(String layout) {
        this.layout = layout;
    }

    public String getAllocation() {
        return allocation;
    }

    public void setAllocation(String allocation) {
        this.allocation = allocation;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getHouse_proprietary_certificate() {
        return house_proprietary_certificate;
    }

    public void setHouse_proprietary_certificate(String house_proprietary_certificate) {
        this.house_proprietary_certificate = house_proprietary_certificate;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}
