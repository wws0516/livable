package com.chuangshu.livable.controller;


import com.chuangshu.livable.base.util.ResultUtil;
import com.chuangshu.livable.base.dto.ResultDTO;
import com.chuangshu.livable.dto.LookDTO;
import com.chuangshu.livable.entity.Looked;
import com.chuangshu.livable.entity.Looking;
import com.chuangshu.livable.service.LookedService;
import com.chuangshu.livable.service.LookingService;
import com.chuangshu.livable.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
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

    @Autowired
    private UserService userService;

    @GetMapping("/getAllLooked")
    @ApiOperation("/查看约看过的记录")
    public ResultDTO getAllLooked(){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        Integer userId = null;
        try {
            userId = Integer.parseInt(request.getSession().getAttribute("userID").toString());
        } catch (Exception e) {
            return ResultUtil.Error("500","请先登录");
        }
        List<Looked> lookedList = null;
        List<LookDTO> lookDTOS = new ArrayList<>();
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

            for(Looked l:lookedList){
                Looking looking = lookingService.get(l.getLookingId());
                lookDTOS.add(new LookDTO(looking,userService.get(userId).getName()));
            }
        } catch (Exception e) {
            ResultUtil.Error("500",e.getMessage());
        }

        return ResultUtil.Success(lookDTOS);
    }



}
