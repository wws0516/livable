package com.chuangshu.livable.service.search.impl;

/**
 * @Author: wws
 * @Date: 2019-09-26 16:41
 */

public class AddressServiceImpl {

//    @Autowired
//    AddressService addressService;
//
//    @Autowired
//    ModelMapper modelMapper;
//
//    @Override
//    public List<LevelAddressDTO> findAllCities(String cityName) {
//
//        List<Address> cities = addressService.findAllByLevel(Address.Level.CITY.getValue());
//        List<LevelAddressDTO> cityDTOs = new ArrayList<>();
//        for (Address supportRegionOrCity : cities){
//            LevelAddressDTO target = modelMapper.map(supportRegionOrCity, LevelAddressDTO.class);
//            cityDTOs.add(target);
//        }
//        return cityDTOs;
//    }
//
//
//    @Override
//    public Map<Address.Level, LevelAddressDTO> findCityAndRegion(String cityName, String regionName) {
//        Map<Address.Level, LevelAddressDTO> result = new HashMap<>();
//        Address city = addressService.findByNameAndLevel(cityName, Address.Level.CITY.getValue());
//        Address region = addressService.findByNameAndBelongTo(regionName, city.getName());
//
//        result.put(Address.Level.CITY, modelMapper.map(city, LevelAddressDTO.class));
//        result.put(Address.Level.REGION, modelMapper.map(region, LevelAddressDTO.class));
//        return result;
//    }
//
//    @Override
//    public List<LevelAddressDTO> findAllRegionsByCityName(String cityName) {
//        if (cityName == null) {
//            return new ArrayList<>();
//        }
//
//        List<LevelAddressDTO> result = new ArrayList<>();
//
//        List<Address> regions = addressService.findAllByLevelAndBelongTo(Address.Level.REGION
//                .getValue(), cityName);
//        for (Address region : regions) {
//            result.add(modelMapper.map(region, LevelAddressDTO.class));
//        }
//        return result;
//    }
}
