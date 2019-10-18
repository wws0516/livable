package com.chuangshu.livable.dto;

import com.chuangshu.livable.base.dto.BaseDTO;
import lombok.Data;

/**
 * @Author: wws
 * @Date: 2019-09-25 17:29
 */
@Data
public class HouseBucketDTO extends BaseDTO {

    /**
     * 聚合bucket的key
     */
    private String key;

    /**
     * 聚合结果值
     */
    private Long count;

    public HouseBucketDTO(String key, Long count) {
        this.key = key;
        this.count = count;
    }
}
