package com.chuangshu.livable.service.impl;

import com.chuangshu.livable.base.service.impl.BaseServiceImpl;
import com.chuangshu.livable.dto.HouseDTO;
import com.chuangshu.livable.entity.House;
import com.chuangshu.livable.entity.MapSearch;
import com.chuangshu.livable.mapper.HouseMapper;
import com.chuangshu.livable.service.HouseService;
import com.chuangshu.livable.service.search.ISearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HouseServiceImpl extends BaseServiceImpl<HouseMapper, House> implements HouseService {

    @Autowired
    ISearchService searchService;

    @Override
    public List<HouseDTO> wholeMapQuery(MapSearch mapSearch) {
        List<Integer> houseIds = searchService.mapQuery(mapSearch.getCityName(), mapSearch.getOrderBy(), mapSearch.getOrderDirection(), mapSearch.getStart(), mapSearch.getSize());
        if (houseIds.size() == 0)
            return null;
        List<HouseDTO> houseDTOS = wrapperHouseResult(houseIds);
        return houseDTOS;
    }

    @Override
    public List<HouseDTO> wrapperHouseResult(List<Integer> houseIds) {

        List<HouseDTO> houseDTOS = new ArrayList<>();
        houseIds.forEach(houseId -> {
            try {
                HouseDTO houseDTO = getDTO(houseId, HouseDTO.class);
                houseDTOS.add(houseDTO);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return houseDTOS;
    }

}
