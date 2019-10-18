package com.chuangshu.livable.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * @Author: wws
 * @Date: 2019-09-26 15:54
 */
@Data
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "belong_to")
    private String belongTo;

    @Column(name = "name")
    private String name;

    private String level;

    @Column(name = "baidu_map_lng")
    private double baiduMapLongitude;

    @Column(name = "baidu_map_lat")
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

        public static Level of(String value){
            for (Level level : Level.values()){
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
