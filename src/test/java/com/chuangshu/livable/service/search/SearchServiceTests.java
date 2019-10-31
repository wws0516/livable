package com.chuangshu.livable.service.search;

import com.chuangshu.livable.LivableApplicationTests;
import com.chuangshu.livable.controller.HouseController;
import com.chuangshu.livable.dto.AddressDTO;
import com.chuangshu.livable.dto.HouseBucketDTO;
import com.chuangshu.livable.dto.LevelBelongToAddressDTO;
import com.chuangshu.livable.dto.NameLevelAddressDTO;
import com.chuangshu.livable.service.AddressService;
import com.chuangshu.livable.service.HouseService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @Author: wws
 * @Date: 2019-09-23 13:32
 */
public class SearchServiceTests extends LivableApplicationTests {

    @Autowired
    private ISearchService searchService;

    @Autowired
    private AddressService addressService;

    @Autowired
    private HouseService houseService;

    @Test
    public void testIndex() throws Exception {


//        for (int i = 1; i < 50; i++){
//            searchService.index(i);
//        }
//
//
//        Integer targetHouseId = 1;
////        RentSearch rentSearch = new RentSearch();
//////        rentSearch.setArea("北京");
////        rentSearch.setAcreageBlock("50-*");
////        rentSearch.setPriceBlock("1000-3000");
////        rentSearch.setKeywords("租到");
////        List houseIds = searchService.countAll(rentSearch);
//        searchService.index(1);
//        searchService.index(2);
//        HouseController houseController = new HouseController();
//
//        AddressDTO nameLevelAddressDTO = new NameLevelAddressDTO("广州", "city");
//        List<AddressDTO> city = addressService.findByParams(nameLevelAddressDTO, AddressDTO.class);
//        if (city.size() == 0) {
//            System.out.println("cw");
//        }
//        AddressDTO levelBelongToAddressDTO = new LevelBelongToAddressDTO("广州", "region");
//        System.out.println(addressService.findByParams(levelBelongToAddressDTO, AddressDTO.class).size());
//
        List<HouseBucketDTO> houseBucketDtos = searchService.mapAggregate("广州");
        System.out.println(houseBucketDtos);

        RentSearch rentSearch = new RentSearch();
        rentSearch.setCity("广州");
        rentSearch.setAcreageBlock("50-*");
        rentSearch.setPriceBlock("1000-3000");
        rentSearch.setKeywords("豪华");
        System.out.println(houseService.query(rentSearch));

    }
}
