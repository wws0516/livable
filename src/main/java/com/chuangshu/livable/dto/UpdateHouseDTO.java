package com.chuangshu.livable.dto;

import com.chuangshu.livable.base.dto.BaseDTO;
import lombok.Data;

@Data
public class UpdateHouseDTO extends HouseDTO {
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

}

