package com.chuangshu.livable.dto;

import com.chuangshu.livable.base.dto.BaseDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Created by 瓦力.
 */
@Data
public class LevelAddressDTO extends AddressDTO {

    private String level;

    public LevelAddressDTO(String level) {
        this.level = level;
    }
}

