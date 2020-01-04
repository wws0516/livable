package com.chuangshu.livable.dto;

import lombok.Data;

import java.util.Date;


@Data
public class HouseTolandlordDTO {

    private Integer houseId;
    private String houseName;
    private Date publishTime;

    public HouseTolandlordDTO(Integer houseId, String houseName, Date publishTime) {
        this.houseId = houseId;
        this.houseName = houseName;
        this.publishTime = publishTime;
    }

    public HouseTolandlordDTO() {
    }
}
