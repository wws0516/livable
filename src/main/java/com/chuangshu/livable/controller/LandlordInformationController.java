package com.chuangshu.livable.controller;

import com.chuangshu.livable.StatusCode.HouseStatusCode;
import com.chuangshu.livable.StatusCode.LandlordStatusCode;
import com.chuangshu.livable.base.util.ResultUtil;
import com.chuangshu.livable.base.dto.ResultDTO;
import com.chuangshu.livable.dto.CheckLandlordDTO;
import com.chuangshu.livable.dto.HouseTolandlordDTO;
import com.chuangshu.livable.entity.House;
import com.chuangshu.livable.entity.LandlordHouseRelation;
import com.chuangshu.livable.entity.LandlordInformation;
import com.chuangshu.livable.entity.UserRole;
import com.chuangshu.livable.service.HouseService;
import com.chuangshu.livable.service.LandlordHouseRelationService;
import com.chuangshu.livable.service.LandlordInformationService;
import com.chuangshu.livable.service.UserRoleService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
public class LandlordInformationController {

    @Autowired
    private LandlordInformationService landlordInformationService;

    @Autowired
    private LandlordHouseRelationService landlordHouseRelationService;

    @Autowired
    private HouseService houseService;

    @Autowired
    UserRoleService userRoleService;

    @ApiOperation("房东注册")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "landlordId", dataType = "Integer", required = true, value = "用户ID"),
            @ApiImplicitParam(paramType = "query", name = "idNumber", dataType = "Long", required = true, value = "身份证号"),
            @ApiImplicitParam(paramType = "query", name = "idCardPictureF", dataType = "String", required = true, value = "身份证正面"),
            @ApiImplicitParam(paramType = "query", name = "idCardPictureR", dataType = "String", required = true, value = "身份证反面"),
            @ApiImplicitParam(paramType = "query", name = "alipayName", dataType = "String", required = true, value = "支付宝账户名"),
            @ApiImplicitParam(paramType = "query", name = "alipayAccount", dataType = "String", required = true, value = "支付宝账号")

    })
    @PostMapping("/registerLandlord")
    public ResultDTO registerLandlord(LandlordInformation landlordInformation)throws Exception{
        LandlordInformation landlordInformation1 = null;
        landlordInformation.setStatus(HouseStatusCode.HOUSE_UNCHECKED.getCode().toString());
        landlordInformation1 = landlordInformationService.save(landlordInformation);

        return ResultUtil.Success(landlordInformation1);
    }

    @ApiOperation("审批房东信息通过")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "landlordId", dataType = "Integer", required = true, value = "房东ID"),
    })
    @GetMapping("/checkLandlordSuccess")
    public ResultDTO checkLandlordSuccess(Integer landlordId) {
        CheckLandlordDTO checkLandlordDTO = new CheckLandlordDTO();
        checkLandlordDTO.setLandlordId(landlordId);
        checkLandlordDTO.setStatus(LandlordStatusCode.LANDLORD_CHECKED_SUCCESS.getCode());
        try {
            landlordInformationService.updateDTO(checkLandlordDTO,LandlordInformation.class);
            userRoleService.save(new UserRole(landlordId, 2));
        } catch (Exception e) {
            return ResultUtil.Error("500","意料之外的错误");
        }
        return ResultUtil.Success();
    }

    @ApiOperation("审批房东信息不通过")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "landlordId", dataType = "Integer", required = true, value = "房东ID"),
    })
    @GetMapping("/checkLandlordFailure")
    public ResultDTO checkLandlordFailure(Integer landlordId) {
        CheckLandlordDTO checkLandlordDTO = new CheckLandlordDTO();
        checkLandlordDTO.setLandlordId(landlordId);
        checkLandlordDTO.setStatus(LandlordStatusCode.LANDLORD_CHECKED_FAILURE.getCode());
        try {
            landlordInformationService.updateDTO(checkLandlordDTO,LandlordInformation.class);
        } catch (Exception e) {
            return ResultUtil.Error("500","意料之外的错误");
        }
        return ResultUtil.Success();
    }

    @PostMapping("/updateLandlordInformation")
    @ApiOperation("修改房东信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "landlordId", dataType = "Integer", required = true, value = "房东ID"),
            @ApiImplicitParam(paramType = "query", name = "userId", dataType = "Integer", required = true, value = "用户ID"),
            @ApiImplicitParam(paramType = "query", name = "idNumber", dataType = "Integer", required = true, value = "身份证号"),
            @ApiImplicitParam(paramType = "query", name = "idCardPictureF", dataType = "String", required = true, value = "身份证正面"),
            @ApiImplicitParam(paramType = "query", name = "idCardPictureR", dataType = "String", required = true, value = "身份证反面"),
            @ApiImplicitParam(paramType = "query", name = "alipayName", dataType = "String", required = true, value = "支付宝账户名"),
            @ApiImplicitParam(paramType = "query", name = "alipayAccount", dataType = "String", required = true, value = "支付宝账号")

    })
    public ResultDTO updateLandlordInformation(LandlordInformation landlordInformation){
        try {
            landlordInformationService.update(landlordInformation);
        } catch (Exception e) {
            return ResultUtil.Error("500","意料之外的错误");
        }

        return ResultUtil.Success();
    }

    @GetMapping("/listAllLandlord")
    @ApiOperation("得到所有房东信息")
    public ResultDTO listAllLandlord(){
        List<LandlordInformation> landlordInformationList = null;
        try {
            landlordInformationList = landlordInformationService.findAll();
        } catch (Exception e) {
            return ResultUtil.Error("500","意料之外的错误");
        }
        return ResultUtil.Success(landlordInformationList);
    }


    @GetMapping("/getAllHouse")
    @ApiOperation("得到所有房源")
    public ResultDTO getAllHouse(HttpServletRequest request) throws Exception{
        List<HouseTolandlordDTO> houseTolandlordDTOList = new ArrayList<>();
        Integer userId = null;
        try {
             userId = (Integer) request.getSession().getAttribute("userID");
        } catch (Exception e) {
            return ResultUtil.Error("501","请先登录");
        }
        LandlordHouseRelation landlordHouseRelation = new LandlordHouseRelation();
        landlordHouseRelation.setUserId(userId);
        List<LandlordHouseRelation> landlordInformationList = landlordHouseRelationService.findByParams(landlordHouseRelation);
        for(LandlordHouseRelation l:landlordInformationList){
            System.out.println(l);
            House house = houseService.get(l.getHouseId());
            houseTolandlordDTOList.add(new HouseTolandlordDTO(house.getHouseId(),house.getTitle(),l.getPublishTime()));
        }
        return ResultUtil.Success(houseTolandlordDTOList);
    }

    @GetMapping("/getLandlordState")
    @ApiOperation("房东状态")
    public ResultDTO getLandlordState(HttpServletRequest request)throws Exception{
        Integer userId = null;
        try {
            userId = (Integer) request.getSession().getAttribute("userID");
        } catch (Exception e) {
            return ResultUtil.Error("501","请先登录");
        }
        return ResultUtil.Success(landlordInformationService.get(userId).getStatus());
    }


}
