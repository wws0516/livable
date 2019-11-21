package com.chuangshu.livable.utils.esUtil;

import com.google.common.collect.ImmutableMap;
import lombok.Data;

import java.util.Map;

/**
 * @Author: wws
 * @Date: 2019-09-25 13:52
 */
@Data
public class RentValueBlock {

    /**
     * 价格区间定义
     */
    public static final Map<String, RentValueBlock> PRICE_BLOCK;

    /**
     * 面积区间定义
     */
    public static final Map<String, RentValueBlock> ACREAGE_BLOCK;

    /**
     * 无限制区间
     */
    public static final RentValueBlock ALL = new RentValueBlock("*", -1, -1);

    static {
        PRICE_BLOCK = ImmutableMap.<String, RentValueBlock>builder()
                .put("*-1000", new RentValueBlock("*-1000", -1, 1000))
                .put("1000-2000", new RentValueBlock("1000-2000", 1000, 2000))
                .put("2000-3000", new RentValueBlock("2000-3000", 2000, 3000))
                .put("3000-*", new RentValueBlock("3000-*", 3000, -1))
                .build();

        ACREAGE_BLOCK = ImmutableMap.<String, RentValueBlock>builder()
                .put("*-50", new RentValueBlock("*-50", -1, 50))
                .put("50-80", new RentValueBlock("50-80", 50, 80))
                .put("80-100", new RentValueBlock("80-100", 80, 100))
                .put("100-120", new RentValueBlock("100-120", 100, 120))
                .put("120-*", new RentValueBlock("120-*", 120, -1))
                .build();
    }

    private String key;
    private int min;
    private int max;

    public RentValueBlock(String key, int min, int max) {
        this.key = key;
        this.min = min;
        this.max = max;
    }

    public static RentValueBlock matchPrice(String key) {
        RentValueBlock block = PRICE_BLOCK.get(key);
        if (block == null) {
            return ALL;
        }
        return block;
    }

    public static RentValueBlock matchAcreage(String key) {
        RentValueBlock block = ACREAGE_BLOCK.get(key);
        if (block == null) {
            return ALL;
        }
        return block;
    }
}
