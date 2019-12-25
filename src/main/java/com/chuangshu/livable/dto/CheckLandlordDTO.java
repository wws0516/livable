package com.chuangshu.livable.dto;

import com.chuangshu.livable.base.dto.BaseDTO;
import lombok.Data;

@Data
public class CheckLandlordDTO extends BaseDTO {

    private Integer LandlordId;

    private String status;
}
