package com.chuangshu.livable.controller;

import com.chuangshu.livable.StatusCode.HouseStatusCode;
import com.chuangshu.livable.base.util.ResultUtil;
import com.chuangshu.livable.base.dto.ResultDTO;
import com.chuangshu.livable.dto.*;
import com.chuangshu.livable.entity.*;
import com.chuangshu.livable.service.*;
import com.chuangshu.livable.service.redis.HouseRedisService;
import com.chuangshu.livable.utils.esUtil.MapSearch;
import com.chuangshu.livable.service.search.ISearchService;
import com.chuangshu.livable.utils.esUtil.RentSearch;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
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
    AllocationService allocationService;

    @Autowired
    FeatureService featureService;

    @Autowired
    AddressService addressService;

    @Autowired
    ISearchService searchService;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    LandlordHouseRelationService landlordHouseRelationService;
    
    @Autowired
    HouseRedisService houseRedisService;

    @Autowired
    LikeHouseService likeHouseService;

    @Autowired
    UserService userService;


    /**
     * 自动补全接口
     */
    @GetMapping("rent/autocomplete")
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
            @ApiImplicitParam(paramType = "query", name = "rentWay", dataType = "String",required = true, value = "租金付费方式"),
            @ApiImplicitParam(paramType = "query", name = "rentWay", dataType = "String",required = true, value = "租房方式"),
            @ApiImplicitParam(paramType = "query", name = "numberOfPeople", dataType = "String",required = true, value = "租房人数"),
            @ApiImplicitParam(paramType = "query", name = "elevator", dataType = "String",required = true, value = "电梯"),
            @ApiImplicitParam(paramType = "query", name = "toward", dataType = "String",required = true, value = "朝向"),
            @ApiImplicitParam(paramType = "query", name = "carport", dataType = "String",required = true, value = "车位有无"),
            @ApiImplicitParam(paramType = "query", name = "energyCharge", dataType = "String",required = true, value = "电费"),
            @ApiImplicitParam(paramType = "query", name = "waterCharge", dataType = "String",required = true, value = "水费"),
            @ApiImplicitParam(paramType = "query", name = "tv", dataType = "Integer",required = true, value = "电视"),
            @ApiImplicitParam(paramType = "query", name = "refrigerator", dataType = "Integer",required = true, value = "冰箱"),
            @ApiImplicitParam(paramType = "query", name = "washingMachine", dataType = "Integer",required = true, value = "洗衣机"),
            @ApiImplicitParam(paramType = "query", name = "airCondition", dataType = "Integer",required = true, value = "空调"),
            @ApiImplicitParam(paramType = "query", name = "wifi", dataType = "Integer",required = true, value = "WI-FI"),
            @ApiImplicitParam(paramType = "query", name = "beds", dataType = "Integer",required = true, value = "床"),
            @ApiImplicitParam(paramType = "query", name = "waterHeater", dataType = "Integer",required = true, value = "热水器"),
            @ApiImplicitParam(paramType = "query", name = "chest", dataType = "Integer",required = true, value = "衣柜"),
            @ApiImplicitParam(paramType = "query", name = "desk", dataType = "Integer",required = true, value = "书桌"),
            @ApiImplicitParam(paramType = "query", name = "cooking", dataType = "Integer",required = true, value = "烹饪"),
            @ApiImplicitParam(paramType = "query", name = "acreage", dataType = "String",required = true, value = "面积"),
            @ApiImplicitParam(paramType = "query", name = "layout", dataType = "String",required = true, value = "布局"),
            @ApiImplicitParam(paramType = "query", name = "houseProprietaryCertificate", dataType = "String",required = true, value = "房产证url"),
            @ApiImplicitParam(paramType = "query", name = "picture", dataType = "String",required = true, value = "图片"),
            @ApiImplicitParam(paramType = "query", name = "independentBathroom", dataType = "Integer",required = true, value = "独立卫浴"),
            @ApiImplicitParam(paramType = "query", name = "independentBalcony", dataType = "Integer",required = true, value = "独立阳台"),
            @ApiImplicitParam(paramType = "query", name = "smartSock", dataType = "Integer",required = true, value = "智能锁"),
            @ApiImplicitParam(paramType = "query", name = "selfDecorating", dataType = "Integer",required = true, value = "可自行装修"),
            @ApiImplicitParam(paramType = "query", name = "firstRent", dataType = "Integer",required = true, value = "首次出租"),
            @ApiImplicitParam(paramType = "query", name = "fullyFurnished", dataType = "Integer",required = true, value = "拎包入住"),
            @ApiImplicitParam(paramType = "query", name = "nearbySubway", dataType = "Integer",required = true, value = "地铁十分钟"),
            @ApiImplicitParam(paramType = "query", name = "anyTimeToSee", dataType = "Integer",required = true, value = "随时看房"),
            @ApiImplicitParam(paramType = "query", name = "checkInAtOnce", dataType = "Integer",required = true, value = "随时入住"),
            @ApiImplicitParam(paramType = "query", name = "introduction", dataType = "String",required = true, value = "介绍"),
            @ApiImplicitParam(paramType = "query", name = "userId", dataType = "Integer",required = true, value = "用户id"),
    })
    public ResultDTO insert(House house, Allocation allocation, Feature feature,Integer userId){
        House saveHouse = null;
        house.setStatus(HouseStatusCode.HOUSE_UNCHECKED.getCode().toString());
        try {
            int allocationId = allocationService.save(allocation).getId();
            int featureId = featureService.save(feature).getId();
            house.setAllocationId(allocationId);
            house.setFeatureId(featureId);
            saveHouse = houseService.save(house);
            LandlordHouseRelation landlordHouseRelation = new LandlordHouseRelation();
            landlordHouseRelation.setUserId(userId);
            landlordHouseRelation.setHouseId(saveHouse.getHouseId());
            landlordHouseRelationService.save(landlordHouseRelation);
            houseRedisService.setHouseDTO(house,feature,allocation);
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
            @ApiImplicitParam(paramType = "query", name = "region", dataType = "String", required = false, value = "地区"),
            @ApiImplicitParam(paramType = "query", name = "priceBlock", dataType = "String", required = false, value = "价格区间"),
            @ApiImplicitParam(paramType = "query", name = "acreageBlock", dataType = "String", required = false, value = "面积区间"),
            @ApiImplicitParam(paramType = "query", name = "rentWay", dataType = "String", required = false, value = "方式"),
            @ApiImplicitParam(paramType = "query", name = "keyWords", dataType = "String", required = false, value = "关键字"),
            @ApiImplicitParam(paramType = "query", name = "toward", dataType = "String", required = false, value = "朝向"),
            @ApiImplicitParam(paramType = "query", name = "feature.independentBathroom", dataType = "Integer",required = true, value = "独立卫浴"),
            @ApiImplicitParam(paramType = "query", name = "feature.independentBalcony", dataType = "Integer",required = true, value = "独立阳台"),
            @ApiImplicitParam(paramType = "query", name = "feature.smartSock", dataType = "Integer",required = true, value = "智能锁"),
            @ApiImplicitParam(paramType = "query", name = "feature.selfDecorating", dataType = "Integer",required = true, value = "可自行装修"),
            @ApiImplicitParam(paramType = "query", name = "feature.firstRent", dataType = "Integer",required = true, value = "首次出租"),
            @ApiImplicitParam(paramType = "query", name = "feature.fullyFurnished", dataType = "Integer",required = true, value = "拎包入住"),
            @ApiImplicitParam(paramType = "query", name = "feature.nearbySubway", dataType = "Integer",required = true, value = "地铁十分钟"),
            @ApiImplicitParam(paramType = "query", name = "feature.anyTimeToSee", dataType = "Integer",required = true, value = "随时看房"),
            @ApiImplicitParam(paramType = "query", name = "feature.checkInAtOnce", dataType = "Integer",required = true, value = "随时入住"),
            @ApiImplicitParam(paramType = "query", name = "orderBy", dataType = "String", required = false, value = "排序字段"),
            @ApiImplicitParam(paramType = "query", name = "orderDirection", dataType = "String", required = false, value = "排序方向"),
            @ApiImplicitParam(paramType = "query", name = "start", dataType = "Integer", required = false, value = "搜索开始位置"),
            @ApiImplicitParam(paramType = "query", name = "size", dataType = "Integer", required = false, value = "搜索个数")
    })
    public ResultDTO searchHouses(RentSearch rentSearch, HttpSession session){
        System.out.println(rentSearch);
        if (rentSearch.getCity() == null){
            String cityNameInSession = (String) session.getAttribute("cityName");
            if (cityNameInSession == null){
                return ResultUtil.Error("202", "请选择城市！");
            }else {
                rentSearch.setCity(cityNameInSession);
            }
        }else {
            session.setAttribute("cityName", rentSearch.getCity());
        }

        AddressDTO city = addressService.findCity(rentSearch.getCity());

        List<AddressDTO> regions = addressService.findAllRegionsByCityName(rentSearch.getCity());
//        if (regions == null || regions.size() < 1) {
//            return ResultUtil.Error("123", "请选择城市！");
//        }

        List<HouseDTO> houseDTOS = houseService.query(rentSearch);

        if (rentSearch.getRegion() == null) {
            rentSearch.setRegion("*");
        }
        return ResultUtil.Success(houseDTOS);
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
            LandlordHouseRelation l = new LandlordHouseRelation();
            l.setHouseId(houseID);
            Integer relationId = landlordHouseRelationService.findByParams(l).get(0).getRelationId();
            landlordHouseRelationService.deleteById(relationId);
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
    public ResultDTO updateHouseByDto(UpdateHouseDTO updateHouseDto,Feature feature,Allocation allocation){
        try{

            houseService.updateDTO(updateHouseDto,House.class);
            featureService.update(feature);
            allocationService.update(allocation);
        }catch (Exception e){
            ResultUtil.Error("500","更新房源信息失败："+e.getMessage());
        }

        try {
            HouseDTO houseDTO = houseService.findByParams(updateHouseDto, HouseDTO.class).get(0);
            if (houseDTO.getStatus().equals(HouseStatusCode.HOUSE_CHECKED.getCode()))
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
    public ResultDTO rentMapPage(@RequestParam(value = "city") String cityName, Model model, HttpSession session, RedirectAttributes redirectAttributes) {
        AddressDTO nameLevelAddressDTO = new NameLevelAddressDTO(cityName, "city");
        try {
            List<AddressDTO>  city = addressService.findByParams(nameLevelAddressDTO, AddressDTO.class);
            if (city.size() == 0) {
                return ResultUtil.Error("123", "请选择城市！");
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
        
        List<HouseBucketDTO> houseBucketDtos = null;
        try {
            regions = addressService.findByParams(levelBelongToAddressDTO, AddressDTO.class);
            houseBucketDtos = searchService.mapAggregate(cityName);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultUtil.Success(houseBucketDtos);
    }

    @GetMapping("/rentMapHouses")
    @ApiOperation("查询某城市的房源")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "cityName", dataType = "String", required = true, value = "城市名")
    })
    @ResponseBody
    public ResultDTO<HouseDTO> rentMapHouses(@ModelAttribute MapSearch mapSearch){
        if(mapSearch.getCityName() == null) {
            return null;
        }

        ResultDTO result = null;

        if(mapSearch.getLevel() < 13){
            List<HouseDTO> houseDTOS = houseService.wholeMapQuery(mapSearch);
            result = ResultUtil.Success(houseDTOS);
        }else {
            List<HouseDTO> houseDTOS = houseService.boundMapQuery(mapSearch);
            result = ResultUtil.Success(houseDTOS);
        }
        return result;
    }


    /**
     * @author 叶三秋
     * @date    2019/11/22
     * 收藏房源接口
     *
     */
    @GetMapping("/addHouseToLike")
    @ApiOperation("收藏房源")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "houseId", dataType = "Integer", required = true, value = "房源id")
    })
    public ResultDTO addHouseToLike(Integer houseId)throws Exception{
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        Integer userId = Integer.parseInt(request.getSession().getAttribute("userID").toString());
        if (houseService.get(houseId)!=null) {
            if (userService.get(userId)!=null) {
                LikeHouse like = new LikeHouse();
                like.setHouseId(houseId);
                like.setUserId(userId);
                if (likeHouseService.findByParams(like).size()==0) {
                    LikeHouse returnResult = likeHouseService.save(like);
                    return ResultUtil.Success(returnResult);
                } else {
                    return ResultUtil.Error("503","该用户已经收藏了该房源");
                }
            }else{
                return ResultUtil.Error("501","请先登录");
            }
        }else {
            return ResultUtil.Error("502","房源不存在");
        }

    }

    @GetMapping("/getLikeHouse")
    @ApiOperation("查找收藏房源")
    public ResultDTO getLikeHouse()throws Exception{
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        Integer userId = Integer.parseInt(request.getSession().getAttribute("userID").toString());
        LikeHouse likeHouse = new LikeHouse();
        likeHouse.setUserId(userId);
        List<LikeHouse> returnResult = likeHouseService.findByParams(likeHouse);
        return ResultUtil.Success(returnResult);
    }
}

