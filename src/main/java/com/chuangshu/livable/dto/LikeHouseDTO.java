package com.chuangshu.livable.dto;

import com.chuangshu.livable.base.dto.BaseDTO;
import com.chuangshu.livable.entity.LikeHouse;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class LikeHouseDTO extends BaseDTO {
    private Integer houseId;
    private String picPath;
    private String address;
    private String price;
    @DateTimeFormat
    private Date date;

    public LikeHouseDTO(Integer houseId,String picPath, String address, String price, Date date) {
        this.houseId = houseId;
        this.picPath = picPath;
        this.address = address;
        this.price = price;
        this.date = date;
    }

    public LikeHouseDTO() {
    }
}
