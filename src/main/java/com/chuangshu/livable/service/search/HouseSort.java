package com.chuangshu.livable.service.search;

import com.google.common.collect.Sets;
import com.tdunning.math.stats.Sort;
import lombok.Data;
import java.util.Set;

/**
 * @Author: wws
 * @Date: 2019-10-25 13:09
 */
@Data
public class HouseSort {
    public static final String DEFAULT_SORT_KEY = "rent";
    private static final Set<String> SORT_KEYS = Sets.newHashSet(
            DEFAULT_SORT_KEY,
            "acreage"
    );

}
