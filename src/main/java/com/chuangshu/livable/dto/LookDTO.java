package com.chuangshu.livable.dto;

import com.chuangshu.livable.base.dto.BaseDTO;
import com.chuangshu.livable.entity.Looking;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author 叶三秋
 * @date 2019/12/14
 */
@Data
public class LookDTO extends BaseDTO {

    private Integer lookId;

    private String landlordInformation;

    private String address;

    @DateTimeFormat
    private Date date;

    public LookDTO(Integer lookId, String landlordInformation, String address, Date date) {
        this.lookId = lookId;
        this.landlordInformation = landlordInformation;
        this.address = address;
        this.date = date;
    }

    public LookDTO() {
    }

    public LookDTO(Looking looking,String name){
        this.lookId = looking.getLookingId();
        this.landlordInformation = name;
        this.address = looking.getSite();
        this.date = looking.getData();
    }
}
