package com.chuangshu.livable.utils.esUtil;

import com.chuangshu.livable.entity.Feature;
import lombok.Data;

/**
 * @Author: wws
 * @Date: 2019-09-25 13:49
 */
@Data
public class RentSearch {

    private String city;
    private String region;
    private String priceBlock;
    private String acreageBlock;
    private String rentWay;
    private String rentType;
    private Feature feature;
    private String keyWords;
    private String orderBy = "rent";
    private String orderDirection = "desc";
    private int start = 0;
    private int size = 20;

    public int getStart() {
        return start > 0 ? start : 0;
    }

    public int getSize() {
        if (this.size < 1) {
            return 5;
        } else if (this.size > 100) {
            return 100;
        } else {
            return this.size;
        }
    }

}
