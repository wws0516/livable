package com.chuangshu.livable.service;

import com.chuangshu.livable.base.service.BaseService;
import com.chuangshu.livable.dto.AddressDTO;
import com.chuangshu.livable.entity.Address;
import com.chuangshu.livable.entity.BaiduMapLocation;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public interface AddressService extends BaseService<Address> {

    public List<AddressDTO> findAllByLevel(String level);

    public AddressDTO findByNameAndLevel(String name, String level);

    public AddressDTO findByNameAndBelongTo(String name, String belongTo);

    public List<AddressDTO> findAllByLevelAndBelongTo(String level, String belongTo);

    public BaiduMapLocation getBaiduMapLocation(String city, String address);

    public List<AddressDTO> findAllCities(String cityName);

    public Map<Address.Level, AddressDTO> findCityAndRegion(String cityName, String regionName);

    public List<AddressDTO> findAllRegionsByCityName(String cityName);

    public AddressDTO findCity(String cityName);

    }
