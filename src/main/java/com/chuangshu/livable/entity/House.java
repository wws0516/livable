package com.chuangshu.livable.entity;

import javax.persistence.*;
import lombok.Data;
import org.apache.kafka.common.protocol.types.Field;
import org.elasticsearch.search.SearchHit;

@Data
public class House {
    @Id
    @Column(name = "house_id")
    private Integer houseId;

    private String title;

    private String city;

    private String region;

    private String address;

    private String houseType;

    private String rent;

    private String rentWay;

    @Column(name = "elevator")
    private char elevator;

    private String toward;

    @Column(name = "carport")
    private char carport;

    private String energyCharge;

    private String waterCharge;

    private String feature;

    private String acreage;

    private String layout;

    private String allocation;

    private String introduction;

    private Integer status;

    private String houseProprietaryCertificate;


    private String picture;





}