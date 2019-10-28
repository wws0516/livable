package com.chuangshu.livable.service.search.impl;

import com.chuangshu.livable.dto.AddressDTO;
import com.chuangshu.livable.dto.LevelAddressDTO;
import com.chuangshu.livable.entity.Address;
import com.chuangshu.livable.service.AddressService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: wws
 * @Date: 2019-09-26 16:41
 */

public class AddressServiceImpl {

    @Autowired
    AddressService addressService;

    @Autowired
    ModelMapper modelMapper;

    public List<AddressDTO> findAllCities(String cityName) {

        List<AddressDTO> cities = addressService.findAllByLevel(Address.Level.CITY.getValue());
        return cities;
    }


    public Map<Address.Level, AddressDTO> findCityAndRegion(String cityName, String regionName) {
        Map<Address.Level, AddressDTO> result = new HashMap<>();
        AddressDTO city = addressService.findByNameAndLevel(cityName, Address.Level.CITY.getValue());
        AddressDTO region = addressService.findByNameAndBelongTo(regionName, city.getName());

        result.put(Address.Level.CITY, city);
        result.put(Address.Level.REGION, region);
        return result;
    }

    public List<AddressDTO> findAllRegionsByCityName(String cityName) {
        if (cityName == null) {
            return new ArrayList<>();
        }

        List<AddressDTO> result = new ArrayList<>();

        List<AddressDTO> regions = addressService.findAllByLevelAndBelongTo(Address.Level.REGION
                .getValue(), cityName);
        for (AddressDTO region : regions) {
            result.add(region);
        }
        return result;
    }
}
