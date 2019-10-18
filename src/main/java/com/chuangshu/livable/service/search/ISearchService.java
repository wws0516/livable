package com.chuangshu.livable.service.search;

import com.chuangshu.livable.dto.HouseBucketDTO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 * 检索接口
 * @Author: wws
 * @Date: 2019-09-23 09:13
 */
@Service
public interface ISearchService {

    /**
     * 索引目标房源
     */
    void index(Integer houseId);

    /**
     * 移除目标房源
     */
    void remove(Integer houseId);

    /**
     * 搜索房源接口
     */
    List<Integer> countAll(RentSearch rentSearch);

    /**
     *  以城市聚合
     */
    List<HouseBucketDTO> mapAggregate(String city);

    /**
     * 城市级别查询
     */
    List<Integer> mapQuery(String cityName, String orderBy, String orderDirection, int start, int size);
}
