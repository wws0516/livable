package com.chuangshu.livable.dto;

import com.chuangshu.livable.base.dto.BaseDTO;
import lombok.Data;

/**
 * @Author: wws
 * @Date: 2019-09-26 20:34
 */
@Data
public class LevelBelongToAddressDTO extends AddressDTO {

    private String belongTo;

    private String level;

    public LevelBelongToAddressDTO(String belongTo, String level) {
        this.belongTo = belongTo;
        this.level = level;
    }

}
