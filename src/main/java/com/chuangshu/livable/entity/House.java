package com.chuangshu.livable.entity;

import java.util.Date;
import javax.persistence.*;
import lombok.Data;

@Data
public class House {
    @Id
    private Integer id;

    private String title;

    private Integer price;

    private Integer area;

    private Integer room;

    private Integer floor;

    @Column(name = "total_floor")
    private Integer totalFloor;

    @Column(name = "watch_times")
    private Integer watchTimes;

    @Column(name = "build_years")
    private Integer buildYears;

    private Integer status;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "last_update_time")
    private Date lastUpdateTime;

    @Column(name = "city_en_name")
    private String cityEnName;

    @Column(name = "region_en_name")
    private String regionEnName;

    private String cover;

    private Integer direction;

    @Column(name = "distance_to_subway")
    private Integer distanceToSubway;

    private Integer parlour;

    private String district;

    @Column(name = "admin_id")
    private Integer adminId;

    private Integer bathroom;

    private String street;

    public static final String ID = "id";

    public static final String TITLE = "title";

    public static final String PRICE = "price";

    public static final String AREA = "area";

    public static final String ROOM = "room";

    public static final String FLOOR = "floor";

    public static final String TOTAL_FLOOR = "totalFloor";

    public static final String WATCH_TIMES = "watchTimes";

    public static final String BUILD_YEARS = "buildYears";

    public static final String STATUS = "status";

    public static final String CREATE_TIME = "createTime";

    public static final String LAST_UPDATE_TIME = "lastUpdateTime";

    public static final String CITY_EN_NAME = "cityEnName";

    public static final String REGION_EN_NAME = "regionEnName";

    public static final String COVER = "cover";

    public static final String DIRECTION = "direction";

    public static final String DISTANCE_TO_SUBWAY = "distanceToSubway";

    public static final String PARLOUR = "parlour";

    public static final String DISTRICT = "district";

    public static final String ADMIN_ID = "adminId";

    public static final String BATHROOM = "bathroom";

    public static final String STREET = "street";
}