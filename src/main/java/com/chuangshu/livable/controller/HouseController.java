package com.chuangshu.livable.controller;

import com.chuangshu.livable.StatusCode.HouseStatusCode;
import com.chuangshu.livable.base.ResultUtil;
import com.chuangshu.livable.base.dto.ResultDTO;
import com.chuangshu.livable.dto.*;
import com.chuangshu.livable.entity.House;
import com.chuangshu.livable.entity.MapSearch;
import com.chuangshu.livable.service.AddressService;
import com.chuangshu.livable.service.HouseService;
import com.chuangshu.livable.service.search.ISearchService;
import com.chuangshu.livable.service.search.RentSearch;
import com.chuangshu.livable.service.search.RentValueBlock;
import com.chuangshu.livable.service.search.impl.AddressServiceImpl;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author：Yesanqiu
 * @Date：2019/07/03
 *
 */

@RequestMapping("/house")
@RestController
public class HouseController {

    @Autowired
    HouseService houseService;

    @Autowired
    AddressService addressService;

    @Autowired
    ISearchService searchService;

    @Autowired
    ModelMapper modelMapper;

    /**
     * 自动补全接口
     */
    @GetMapping("rent/autocomplete")
    @ResponseBody
    @ApiOperation(value = "搜索时提示功能")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "prefix", dataType = "String",required = true, value = "前缀")
    })
    public List<String> autocomplete(@RequestParam(value = "prefix") String prefix) {

        if (prefix.isEmpty()) {
            return null;
        }
        List<String> suggest = this.searchService.suggest(prefix);
        return suggest;
    }

    @PostMapping("/insert")
    @ApiOperation(value = "新增房源信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "title", dataType = "String",required = true, value = "标题"),
            @ApiImplicitParam(paramType = "query", name = "area", dataType = "String",required = true, value = "地区"),
            @ApiImplicitParam(paramType = "query", name = "address", dataType = "String",required = true, value = "地址"),
            @ApiImplicitParam(paramType = "query", name = "houseType", dataType = "String",required = true, value = "房型"),
            @ApiImplicitParam(paramType = "query", name = "rent", dataType = "String",required = true, value = "租金"),
            @ApiImplicitParam(paramType = "query", name = "rentWay", dataType = "String",required = true, value = "方式"),
            @ApiImplicitParam(paramType = "query", name = "elevator", dataType = "String",required = true, value = "电梯"),
            @ApiImplicitParam(paramType = "query", name = "toward", dataType = "String",required = true, value = "朝向"),
            @ApiImplicitParam(paramType = "query", name = "carport", dataType = "String",required = true, value = "车位有无"),
            @ApiImplicitParam(paramType = "query", name = "energyCharge", dataType = "String",required = true, value = "电费"),
            @ApiImplicitParam(paramType = "query", name = "waterCharge", dataType = "String",required = true, value = "水费"),
            @ApiImplicitParam(paramType = "query", name = "feature", dataType = "String",required = true, value = "特色"),
            @ApiImplicitParam(paramType = "query", name = "acreage", dataType = "String",required = true, value = "面积"),
            @ApiImplicitParam(paramType = "query", name = "layout", dataType = "String",required = true, value = "布局"),
            @ApiImplicitParam(paramType = "query", name = "status", dataType = "String",required = false, value = "状态"),
            @ApiImplicitParam(paramType = "query", name = "houseProprietaryCertificate", dataType = "String",required = true, value = "房产证url"),
            @ApiImplicitParam(paramType = "query", name = "picture", dataType = "String",required = true, value = "图片"),
            @ApiImplicitParam(paramType = "query", name = "allocation", dataType = "String",required = true, value = "房屋配置"),
            @ApiImplicitParam(paramType = "query", name = "introduction", dataType = "String",required = true, value = "介绍"),
    })
    public ResultDTO<House> insertOneHouse(House house){
        House saveHouse = null;
        house.setStatus(HouseStatusCode.HOUSE_UNCHECKED.getCode());
        try {
            saveHouse = houseService.save(house);
//            //新增es索引
//            searchService.index(house.getHouseId());
        } catch (Exception e) {
            ResultUtil.Error("500","新建房源信息失败："+e.getMessage());
        }
        return ResultUtil.Success(saveHouse);
    }

    @PostMapping("/search")
    @ApiOperation(value = "搜索房源信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "city", dataType = "String", required = true, value = "城市"),
            @ApiImplicitParam(paramType = "query", name = "region", dataType = "String", required = true, value = "地区"),
            @ApiImplicitParam(paramType = "query", name = "priceBlock", dataType = "String", required = true, value = "价格区间"),
            @ApiImplicitParam(paramType = "query", name = "acreageBlock", dataType = "String", required = true, value = "面积区间"),
            @ApiImplicitParam(paramType = "query", name = "rentWay", dataType = "String", required = true, value = "方式"),
            @ApiImplicitParam(paramType = "query", name = "keyWords", dataType = "String", required = true, value = "关键字"),
            @ApiImplicitParam(paramType = "query", name = "toward", dataType = "String", required = true, value = "朝向"),
            @ApiImplicitParam(paramType = "query", name = "orderBy", dataType = "String", required = true, value = "排序字段"),
            @ApiImplicitParam(paramType = "query", name = "orderDirection", dataType = "String", required = true, value = "排序方向"),
            @ApiImplicitParam(paramType = "query", name = "start", dataType = "Integer", required = true, value = "搜索开始位置"),
            @ApiImplicitParam(paramType = "query", name = "start", dataType = "Integer", required = true, value = "搜索个数")
    })
    public String searchHouses(@ModelAttribute RentSearch rentSearch, Model model, HttpSession session, RedirectAttributes redirectAttributes){

        if (rentSearch.getCity() == null){
            String cityNameInSession = (String) session.getAttribute("cityName");
            if (cityNameInSession == null){
                redirectAttributes.addAttribute("msg", "请选择城市！");
            }else {
                rentSearch.setCity(cityNameInSession);
            }
        }else {
            session.setAttribute("cityName", rentSearch.getCity());
        }

        AddressDTO city = addressService.findCity(rentSearch.getCity());
        if (city == null) {
            redirectAttributes.addAttribute("msg", "must_chose_city");
            return "redirect:/index";
        }
        model.addAttribute("currentCity", city);

        List<AddressDTO> regions = addressService.findAllRegionsByCityName(rentSearch.getCity());
        if (regions == null || regions.size() < 1) {
            redirectAttributes.addAttribute("msg", "must_chose_city");
            return "redirect:/index";
        }

        List<HouseDTO> houseDTOS = houseService.query(rentSearch);

        model.addAttribute("total", houseDTOS.size());
        model.addAttribute("houses", houseDTOS);

        if (rentSearch.getRegion() == null) {
            rentSearch.setRegion("*");
        }

        model.addAttribute("searchBody", rentSearch);
        model.addAttribute("regions", regions);

        model.addAttribute("priceBlocks", RentValueBlock.PRICE_BLOCK);
        model.addAttribute("acreageBlocks", RentValueBlock.ACREAGE_BLOCK);

        model.addAttribute("currentPriceBlock", RentValueBlock.matchPrice(rentSearch.getPriceBlock()));
        model.addAttribute("currentAcreageBlock", RentValueBlock.matchAcreage(rentSearch.getAcreageBlock()));

        return "rent-list";

    }

    @GetMapping("/getOneHouse")
    @ApiOperation("通过Id查房源信息")
    @ApiImplicitParam(paramType = "query", name = "houseID", dataType = "Integer",required = true, value = "房源ID")
    public ResultDTO getOneHouse(Integer houseID){
        House house = null;
        try {
            house = houseService.get(houseID);
        } catch (Exception e) {
            ResultUtil.Error("500","查无此房源："+e.getMessage());
        }
        return ResultUtil.Success(house);
    }



    public ResultDTO getHouseByParams(){
        return ResultUtil.Success();
    }

    @DeleteMapping("/deleteHouse")
    @ApiOperation("删除房源信息")
    @ApiImplicitParam(paramType = "query", name = "houseID", dataType = "Integer",required = true, value = "房源ID")
    public ResultDTO deleteHouse(Integer houseID){
        try {
            houseService.deleteById(houseID);
            //删除es索引
            searchService.remove(houseID);
        } catch (Exception e) {
            ResultUtil.Error("500","删除房源失败："+e.getMessage());
        }
        return ResultUtil.Success();
    }

    @PostMapping("/updateHouseByDto")
    @ApiOperation("通过模板更新房源信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "title", dataType = "String",required = true, value = "标题"),
            @ApiImplicitParam(paramType = "query", name = "area", dataType = "String",required = true, value = "地区"),
            @ApiImplicitParam(paramType = "query", name = "address", dataType = "String",required = true, value = "地址"),
            @ApiImplicitParam(paramType = "query", name = "houseType", dataType = "String",required = true, value = "房型"),
            @ApiImplicitParam(paramType = "query", name = "rent", dataType = "String",required = true, value = "租金"),
            @ApiImplicitParam(paramType = "query", name = "rentWay", dataType = "String",required = true, value = "方式"),
            @ApiImplicitParam(paramType = "query", name = "elevator", dataType = "String",required = true, value = "电梯"),
            @ApiImplicitParam(paramType = "query", name = "toward", dataType = "String",required = true, value = "朝向"),
            @ApiImplicitParam(paramType = "query", name = "carport", dataType = "String",required = true, value = "车位有无"),
            @ApiImplicitParam(paramType = "query", name = "energyCharge", dataType = "String",required = true, value = "电费"),
            @ApiImplicitParam(paramType = "query", name = "waterCharge", dataType = "String",required = true, value = "水费"),
            @ApiImplicitParam(paramType = "query", name = "feature", dataType = "String",required = true, value = "特色"),
            @ApiImplicitParam(paramType = "query", name = "acreage", dataType = "String",required = true, value = "面积"),
            @ApiImplicitParam(paramType = "query", name = "layout", dataType = "String",required = true, value = "布局"),
            @ApiImplicitParam(paramType = "query", name = "houseProprietaryCertificate", dataType = "String",required = true, value = "房产证url"),
            @ApiImplicitParam(paramType = "query", name = "picture", dataType = "String",required = true, value = "图片"),
            @ApiImplicitParam(paramType = "query", name = "allocation", dataType = "String",required = true, value = "房屋配置"),
            @ApiImplicitParam(paramType = "query", name = "introduction", dataType = "String",required = true, value = "介绍"),
    })
    public ResultDTO updateHouseByDto(UpdateHouseDTO updateHouseDto){
        try{
            houseService.updateDTO(updateHouseDto,House.class);

        }catch (Exception e){
            ResultUtil.Error("500","更新房源信息失败："+e.getMessage());
        }

        try {
            HouseDTO houseDTO = houseService.findByParams(updateHouseDto, HouseDTO.class).get(0);
            if (houseDTO.getStatus() == HouseStatusCode.HOUSE_CHECKED.getCode())
                searchService.index(houseDTO.getHouseId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultUtil.Success();
    }

    @GetMapping("/rentMap")
    @ApiOperation("查询城市下的区(县)的房源数")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "city", dataType = "String", required = true, value = "城市")
    })
    public String rentMapPage(@RequestParam(value = "city") String cityName, Model model, HttpSession session, RedirectAttributes redirectAttributes) {
        AddressDTO nameLevelAddressDTO = new NameLevelAddressDTO(cityName, "city");
        try {
            List<AddressDTO>  city = addressService.findByParams(nameLevelAddressDTO, AddressDTO.class);
            if (city.size() == 0) {
                redirectAttributes.addAttribute("msg", "must_choose_city");
                return "redirect:/html/index.html";
            }
            else{
                session.setAttribute("cityName", cityName);
                model.addAttribute("city", city.get(0));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        LevelBelongToAddressDTO levelBelongToAddressDTO = new LevelBelongToAddressDTO(cityName, "region");
        List<AddressDTO> regions = null;
        try {
            regions = addressService.findByParams(levelBelongToAddressDTO, AddressDTO.class);
            List<HouseBucketDTO> houseBucketDtos = searchService.mapAggregate(cityName);
            model.addAttribute("aggDate", houseBucketDtos);
            model.addAttribute("total", houseBucketDtos.size());
            model.addAttribute("regions", regions);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "rentMap";
    }

    @GetMapping("/rentMapHouses")
    @ApiOperation("查询某城市的房源")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "cityName", dataType = "String", required = true, value = "城市名")
    })
    @ResponseBody
    public ApiResponse rentMapHouses(@ModelAttribute MapSearch mapSearch){
        if (mapSearch.getCityName() == null)
            return null;
        if(mapSearch.getLevel() < 13){
            houseService.wholeMapQuery(mapSearch);
        }else {}
    return null;
    }
}

