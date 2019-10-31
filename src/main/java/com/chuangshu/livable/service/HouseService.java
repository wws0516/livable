package com.chuangshu.livable.service;

import com.chuangshu.livable.base.service.BaseService;
import com.chuangshu.livable.dto.HouseDTO;
import com.chuangshu.livable.entity.House;
import com.chuangshu.livable.entity.MapSearch;
import com.chuangshu.livable.service.search.RentSearch;

import java.util.List;

public interface HouseService extends BaseService<House> {

    List<HouseDTO> wholeMapQuery(MapSearch mapSearch);

    List<HouseDTO> wrapperHouseResult(List<Integer> houseIds);

    List<HouseDTO> query(RentSearch rentSearch);

//    List<HouseDTO> simpleQuery(RentSearch rentSearch);
}
