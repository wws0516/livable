package com.chuangshu.livable.service.search;

/**
 * @Author: wws
 * @Date: 2019-09-24 09:39
 */
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

    public Integer getHouseId() {
        return houseId;
    }

    public void setHouseId(Integer houseId) {
        this.houseId = houseId;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public int getRetry() {
        return retry;
    }

    public void setRetry(int retry) {
        this.retry = retry;
    }
}
