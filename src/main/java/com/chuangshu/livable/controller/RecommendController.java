package com.chuangshu.livable.controller;

import com.chuangshu.livable.StatusCode.HouseStatusCode;
import com.chuangshu.livable.base.page.PageList;
import com.chuangshu.livable.base.page.Paginator;
import com.chuangshu.livable.dto.ReturnHouseDTO;
import com.chuangshu.livable.entity.Allocation;
import com.chuangshu.livable.entity.Feature;
import com.chuangshu.livable.entity.House;
import com.chuangshu.livable.entity.User;
import com.chuangshu.livable.service.AllocationService;
import com.chuangshu.livable.service.FeatureService;
import com.chuangshu.livable.service.HouseService;
import com.chuangshu.livable.service.redis.UserRedisService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @auther zhang
 * @ TO DO 推荐房源相关接口
 * @creatTime 2019/11/9 20:33
 **/
@RequestMapping("/recommend")
@RestController
public class RecommendController {

    @Autowired
    private UserRedisService userRedisService;

    @Autowired
    private HouseService houseService;

    @Autowired
    private FeatureService featureService;

    @Autowired
    private AllocationService allocationService;

    @GetMapping("/getRecommend")
    @ApiOperation("获取用户的推荐列表")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "pageNum", dataType = "Integer", defaultValue = "1", required = true, value = "页码"),
            @ApiImplicitParam(paramType = "query", name = "pageSize", dataType = "Integer", defaultValue = "5", required = false, value = "每页容量"),
            @ApiImplicitParam(paramType = "query", name = "pageNum", dataType = "Integer", defaultValue = "1", required = true, value = "页码"),
            @ApiImplicitParam(paramType = "query", name = "userId", dataType = "string", defaultValue = "0", required = false, value = "用户id")
    })
    public PageList<ReturnHouseDTO> selectEntityPage(User user, int pageNum, int pageSize)throws Exception{
        List<Integer> houserIdList = userRedisService.userGetRecommend(user);
        Page<Object> objects = PageHelper.startPage(pageNum, pageSize);
        PageList<ReturnHouseDTO> returnHouseDTOPageList = new PageList<>(pageNum,pageSize,houserIdList.size());
        int start = 0 + pageNum*pageSize;
        int end = pageSize+pageNum*pageSize;
        ArrayList<ReturnHouseDTO> resultList = new ArrayList<>();
        for (int i =start;i<end;i++){
            Integer houseID = houserIdList.get(i);
            House house = houseService.get(houseID);
            /*System.out.println(i);
            House house = houseService.get(i);*/
            if(house.getStatus().equals(HouseStatusCode.HOUSE_CHECKED_SUCCESS.getCode().toString())) {
                Feature feature = featureService.get(house.getFeatureId());
                Allocation allocation = allocationService.get(house.getAllocationId());
                ReturnHouseDTO returnHouseDTO = new ReturnHouseDTO(house, feature, allocation);
                returnHouseDTO.setHouseId(house.getHouseId());
                resultList.add(returnHouseDTO);
            }
        }
        returnHouseDTOPageList.setData(resultList);
        return returnHouseDTOPageList;
    }
}
