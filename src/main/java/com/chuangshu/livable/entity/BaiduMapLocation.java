package com.chuangshu.livable.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @Author: wws
 * @Date: 2019-10-07 21:45
 */
@Data
public class BaiduMapLocation {

    @JsonProperty("lon")
    private double longitude;

    @JsonProperty("lat")
    private double latitude;
}
