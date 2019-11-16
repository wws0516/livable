package com.chuangshu.livable.dto;

import com.chuangshu.livable.base.dto.BaseDTO;
import com.chuangshu.livable.entity.Address;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @Author: wws
 * @Date: 2019-09-26 21:38
 */
@Data
public class AddressDTO extends BaseDTO {


    private Long id;

    private String belongTo;

    private String name;

    private String level;

    private double baiduMapLongitude;

    private double baiduMapLatitude;


    /**
     * 行政级别
     */
    public enum Level{
        CITY("city"),
        REGION("region");

        private String value;

        Level(String value){
            this.value = value;
        }

        public static Address.Level of(String value){
            for (Address.Level level : Address.Level.values()){
                if (level.getValue().equals(value)){
                    return level;
                }
            }

            throw new IllegalArgumentException();
        }

        public String getValue() {
            return value;
        }
    }
}
