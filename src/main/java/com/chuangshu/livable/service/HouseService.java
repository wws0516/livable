package com.chuangshu.livable.service;

import com.chuangshu.livable.base.service.BaseService;
import com.chuangshu.livable.dto.HouseDTO;
import com.chuangshu.livable.entity.House;
import com.chuangshu.livable.utils.esUtil.MapSearch;
import com.chuangshu.livable.utils.esUtil.RentSearch;

import java.util.List;

public interface HouseService extends BaseService<House> {

    List<HouseDTO> wholeMapQuery(MapSearch mapSearch);

    List<HouseDTO> wrapperHouseResult(List<Integer> houseIds);

    List<HouseDTO> query(RentSearch rentSearch);

//    List<HouseDTO> simpleQuery(RentSearch rentSearch);
}
