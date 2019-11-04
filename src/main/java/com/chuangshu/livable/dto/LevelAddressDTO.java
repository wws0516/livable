package com.chuangshu.livable.dto;

import lombok.Data;

/**
 * Created by wws.
 */
@Data
public class LevelAddressDTO extends AddressDTO {

    private String level;

    public LevelAddressDTO(String level) {
        this.level = level;
    }
}

