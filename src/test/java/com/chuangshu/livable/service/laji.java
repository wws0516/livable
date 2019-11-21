package com.chuangshu.livable.service;

import com.chuangshu.livable.DataA;
import com.chuangshu.livable.DataB;
import com.chuangshu.livable.redis.HouseRedisDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;

/**
 * @auther zhang
 * @ TO DO
 * @creatTime 2019/11/7 16:01
 **/

public class laji {


    @Test
    public void test1() throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        DataA dataA = new DataA("sdafsa");
        System.out.println(dataA);
        DataB dataB = new DataB();
        System.out.println(dataB);
        BeanUtils.copyProperties(dataB, dataA);
        System.out.println(dataB);
        System.out.println(dataA);
    }

    @Test
    public void test2() throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        String houseType = "2室1厅1卫";
        String[] split = houseType.split("[室厅卫]");
        for (String s : split) {
            System.out.println(s);
        }
    }

    @Test
    public void test3(){
        for (int i = 0;i<100;i++){
            System.out.println(Math.random());
        }

    }

    @Test
    public void test4(){
        Double[] newfields = new Double[3];
        for (int i = 0; i < newfields.length; i++) {
            newfields[i] = 0d;

        }
        for (Double newfield : newfields) {
            System.out.println(newfield);
        }
    }


}
