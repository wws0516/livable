package com.chuangshu.livable.service.search;

import com.chuangshu.livable.entity.BaiduMapLocation;
import lombok.Data;

import java.util.List;

/**
 * @Author: wws
 * @Date: 2019-09-23 08:30
 */
@Data
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
    private String toward;
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
    private List<HouseSuggest> suggests;

}
