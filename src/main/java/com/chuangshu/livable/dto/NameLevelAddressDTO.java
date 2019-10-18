package com.chuangshu.livable.dto;

import com.chuangshu.livable.base.dto.BaseDTO;
import lombok.Data;

/**
 * @Author: wws
 * @Date: 2019-09-26 20:34
 */
@Data
public class NameLevelAddressDTO extends AddressDTO {

    private String name;

    private String level;

    public NameLevelAddressDTO(String name, String level) {
        this.name = name;
        this.level = level;
    }

}
