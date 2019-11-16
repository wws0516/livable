package com.chuangshu.livable.service.impl;

import com.chuangshu.livable.base.service.impl.BaseServiceImpl;
import com.chuangshu.livable.base.util.modelmapper.ToAllocation;
import com.chuangshu.livable.base.util.modelmapper.ToFeature;
import com.chuangshu.livable.dto.HouseDTO;
import com.chuangshu.livable.entity.House;
import com.chuangshu.livable.service.FeatureService;
import com.chuangshu.livable.utils.esUtil.MapSearch;
import com.chuangshu.livable.mapper.HouseMapper;
import com.chuangshu.livable.service.HouseService;
import com.chuangshu.livable.service.search.ISearchService;
import com.chuangshu.livable.utils.esUtil.RentSearch;
import io.swagger.models.auth.In;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HouseServiceImpl extends BaseServiceImpl<HouseMapper, House> implements HouseService {

    @Autowired
    ISearchService searchService;
    @Autowired
    FeatureService featureService;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    ToAllocation toAllocation;
    @Autowired
    ToFeature toFeature;

    static int mapping = 0;

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
                System.out.println(getDTO(houseId, HouseDTO.class));
                House house = get(houseId);
                //创建自定义映射规则
                PropertyMap<House, HouseDTO> propertyMap = new PropertyMap<House, HouseDTO>() {
                    @Override
                    protected void configure() {
                        using(toFeature.getToFeature()).map(source.getFeatureId(),destination.getFeature());//使用自定义转换规则
                        using(toAllocation.getToAllocation()).map(source.getAllocationId(),destination.getAllocation());//使用自定义转换规则
                    }
                };

                if (mapping<1) {
                    //添加映射器
                    modelMapper.addMappings(propertyMap);
                    mapping++;
                }
                modelMapper.validate();
                modelMapper.map(house, HouseDTO.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return houseDTOS;
    }


    @Override
    public List<HouseDTO> query(RentSearch rentSearch) {
            List<Integer> houseIds = searchService.query(rentSearch);
            if (houseIds.size() == 0){
                return null;
            }
            return wrapperHouseResult(houseIds);
    }

    @Override
    public List<HouseDTO> boundMapQuery(MapSearch mapSearch) {

        List<HouseDTO> houseDTOS = new ArrayList<>();

        List<Integer> houseIds = searchService.mapQuery(mapSearch);
        if (houseIds.size()==0 || houseIds==null)
            return houseDTOS;
        houseDTOS = wrapperHouseResult(houseIds);
        return houseDTOS;
    }
}
