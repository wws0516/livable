package com.chuangshu.livable.entity;

import lombok.Data;

/**
 * @Author: wws
 * @Date: 2019-10-09 15:16
 */
@Data
public class MapSearch {

    private String cityName;
    /**
     * 地图缩放级别
     */
    private int level = 12;
    private String orderBy = "rent";
    private String orderDirection = "desc";
    /**
     * 左上角
     */
    private Double leftLongitude;
    private Double leftLatitude;

    /**
     * 右下角
     */
    private Double rightLongitude;
    private Double rightLatitude;

    private int start = 0;
    private int size = 5;

}
