package com.chuangshu.livable.service.search;

import lombok.Data;

/**
 * @Author: wws
 * @Date: 2019-10-25 16:48
 */
@Data
public class HouseSuggest {
    private String input;
    private int weight = 10;
}
