package com.chuangshu.livable.controller;


import com.chuangshu.livable.base.ResultUtil;
import com.chuangshu.livable.base.dto.ResultDTO;
import com.chuangshu.livable.entity.Looked;
import com.chuangshu.livable.entity.Looking;
import com.chuangshu.livable.service.LookedService;
import com.chuangshu.livable.service.LookingService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * @author 叶三秋
 * @date 2019/09/10
 */
@RestController
public class LookedController {

    @Autowired
    LookedService lookedService;

    @Autowired
    LookingService lookingService;

    @GetMapping("/getAllLooked")
    @ApiOperation("/查看约看过的记录")
    public ResultDTO getAllLooked(){
        List<Looked> lookedList = null;
        try {
            List<Looking> lookingList = lookingService.findAll();
            Iterator<Looking> iterator = lookingList.iterator();
            Date date = new Date();
            if(iterator.hasNext()){
                Looking looking = iterator.next();
                if(looking.getData().before(date)){
                    Looked looked = new Looked();
                    looked.setLookingId(looking.getLookingId());
                    System.out.println(lookedService.findByParams(looked).size());
                    if(lookedService.findByParams(looked).size()==0){
                        lookedService.save(looked);
                    }
                }
            }
            lookedList = lookedService.findAll();
        } catch (Exception e) {
            ResultUtil.Error("500",e.getMessage());
        }

        return ResultUtil.Success(lookedList);
    }



}
