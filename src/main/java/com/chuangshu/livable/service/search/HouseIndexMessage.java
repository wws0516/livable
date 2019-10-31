package com.chuangshu.livable.service.search;

import lombok.Data;

/**
 * @Author: wws
 * @Date: 2019-09-24 09:39
 */
@Data
public class HouseIndexMessage {

    public static final String INDEX = "index";
    public static final String REMOVE = "remove";
    public static final Integer MAX_RETRY = 3;


    private Integer houseId;
    private String operation;
    private int retry = 0;

    public HouseIndexMessage(){

    }

    public HouseIndexMessage(Integer houseId, String operation, int retry){
        this.houseId = houseId;
        this.operation = operation;
        this.retry = retry;
    }

}
