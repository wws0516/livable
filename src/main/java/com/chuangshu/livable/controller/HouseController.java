package com.chuangshu.livable.controller;

import com.chuangshu.livable.StatusCode.StatusCode;
import com.chuangshu.livable.base.ResultUtil;
import com.chuangshu.livable.base.dto.ResultDTO;
import com.chuangshu.livable.dto.HouseES;
import com.chuangshu.livable.dto.UpdateHouseDto;
import com.chuangshu.livable.entity.House;
import com.chuangshu.livable.entity.LandlordHouseRelation;
import com.chuangshu.livable.repository.HouseESRepository;
import com.chuangshu.livable.service.HouseService;
import com.chuangshu.livable.service.LandlordHouseRelationService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
    private HouseService houseService;

    @Autowired
    private HouseESRepository houseESRepository;

    @Autowired
    private LandlordHouseRelationService landlordHouseRelationService;

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
    public ResultDTO<House> insertOneHouse(House house,Integer userId)throws Exception{
        House saveHouse = null;
        HouseES houseES = null;
        System.out.println(house);
        house.setStatus(StatusCode.HOUSE_UNCHECKED.getCode());
        saveHouse = houseService.save(house);
        LandlordHouseRelation landlordHouseRelation = new LandlordHouseRelation();
        landlordHouseRelation.setHouseId(saveHouse.getHouseId());
        landlordHouseRelation.setUserId(userId);
        landlordHouseRelationService.save(landlordHouseRelation);
        return ResultUtil.Success(houseES);
    }

    @GetMapping("/getOneHouse")
    @ApiOperation("通过Id查房源信息")
    @ApiImplicitParam(paramType = "query", name = "houseID", dataType = "Integer",required = true, value = "房源ID")
    public ResultDTO getOneHouse(Integer houseID){
        House house = null;
        try {
            house  = houseService.get(houseID);
        } catch (Exception e) {
            ResultUtil.Error("500","查无此房源："+e.getMessage());
        }
        return ResultUtil.Success(house);
    }

    @GetMapping("/getHouseByUserId")
    @ApiOperation("用用户id得到房源")
    @ApiImplicitParam(paramType = "query", name = "userId", dataType = "Integer", required = true, value = "用户ID")
    public ResultDTO getHouseByUserId(Integer userId){
        List<LandlordHouseRelation> landlordHouseRelation = null;
        LandlordHouseRelation find = new LandlordHouseRelation();
        List<House> h = new ArrayList<>();
        find.setHouseId(userId);
        try {
            landlordHouseRelation = landlordHouseRelationService.findByParams(find);
            for(LandlordHouseRelation l:landlordHouseRelation){
                House house = houseService.get(l.getHouseId());
                h.add(house);
            }
        } catch (Exception e) {
            return ResultUtil.Error("500","意料之外的错误");
        }

        return ResultUtil.Success(h);
    }


    @GetMapping("/getHouseByKeyword")
    public ResultDTO getHouseByParams(House house){
//        // 构建查询条件
//        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
//        // 添加基本分词查询
//        queryBuilder.withQuery(QueryBuilders.matchQuery("area", houseES.getArea()));
//        // 搜索，获取结果
//        Page<HouseES> items = houseESRepository.search(queryBuilder.build());
//        // 总条数
//        long total = items.getTotalElements();
//        searchs += "总共数据数：" + total + "\n";
//        items.forEach(userES -> {
//            searchs += userES.toString() + "\n";
//        });
        List<House> houseList = null;
        try {
            houseList = houseService.findByParams(house);
        }catch (Exception e){
            return ResultUtil.Error("500","发生预料之外的错误");
        }

        return ResultUtil.Success(houseList);
    }

    @DeleteMapping("/deleteHouse")
    @ApiOperation("删除房源信息")
    @ApiImplicitParam(paramType = "query", name = "houseID", dataType = "Integer",required = true, value = "房源ID")
    public ResultDTO deleteHouse(Integer houseID){
        try {
            houseService.deleteById(houseID);
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
    public ResultDTO updateHouseByDto(UpdateHouseDto updateHouseDto){
        try{
            houseService.updateDTO(updateHouseDto,House.class);
        }catch (Exception e){
            ResultUtil.Error("500","更新房源信息失败："+e.getMessage());
        }
        return ResultUtil.Success();
    }

    @ApiOperation("审批房源信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "houseId", dataType = "Integer", required = true, value = "房源ID"),
            @ApiImplicitParam(paramType = "query", name = "code", dataType = "String", required = true, value = "房源信息状态"),
    })
    @GetMapping("/checkHouse")
    public ResultDTO checkHouse(Integer houseId,String code){
        House house = new House();
        house.setHouseId(houseId);
        if (code.equals("U")) {
            house.setStatus(StatusCode.HOUSE_UNCHECKED.getCode());

        }else if(code.equals("C")){
            house.setStatus(StatusCode.HOUSE_CHECKED.getCode());
        }
        try {
            houseService.update(house);
        } catch (Exception e) {
            return ResultUtil.Error("500","意料之外的错误");
        }
        return ResultUtil.Success();
    }
}
