package com.chuangshu.livable.dto;

import com.chuangshu.livable.base.dto.BaseDTO;
import lombok.Data;

@Data
public class HouseCheckDTO extends BaseDTO {

    private Integer houseId;

    private String status;
}
