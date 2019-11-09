package com.chuangshu.livable.utils.redisUtil;

import com.chuangshu.livable.redis.HouseRedisDTO;

import java.lang.reflect.Field;

/**
 * @auther zhang
 * @ TO DO
 * @creatTime 2019/11/8 21:45
 **/
public class HouseRedisUtil {

    public static Double cosDistance(HouseRedisDTO houseRedisDTOa, HouseRedisDTO houseRedisDTOb) throws IllegalAccessException {
        Field[] houseFields = HouseRedisDTO.class.getDeclaredFields();
        Double aMulb = 0d;
        Double aMula = 0d;
        Double bMulb = 0d;
        //System.out.println(houseFields.length);
        for (int i = 0; i < houseFields.length; i++) {
            houseFields[i].setAccessible(true);
            Double a = (Double) houseFields[i].get(houseRedisDTOa);
            //System.out.println("a."+houseFields[i].getName()+"----"+a);
            Double b = (Double) houseFields[i].get(houseRedisDTOb);
            //System.out.println("b."+houseFields[i].getName()+"----"+b);
            aMulb += a*b;
            aMula += a*a;
            bMulb += b*b;
        }
        double result = aMulb / (Math.sqrt(aMula) * Math.sqrt(bMulb));
        return result;
    }

    public static Double NormailCosDistance(HouseRedisDTO houseRedisDTOa, HouseRedisDTO houseRedisDTOb,
                                            Double rentMax, Double renMin,Double arcMax,Double arcMin) throws IllegalAccessException {
        Field[] houseFields = HouseRedisDTO.class.getDeclaredFields();
        Double aMulb = 0d;
        Double aMula = 0d;
        Double bMulb = 0d;
        //System.out.println(houseFields.length);
        for (int i = 0; i < houseFields.length; i++) {

            houseFields[i].setAccessible(true);
            Double a = (Double) houseFields[i].get(houseRedisDTOa);
            Double b = (Double) houseFields[i].get(houseRedisDTOb);
            if (houseFields[i].getName().equals("rent")) {
                a = (a - renMin)/(rentMax - renMin);
                b = (b- renMin)/(rentMax - renMin);
            }
            if (houseFields[i].getName().equals("acreage")){
                a = (a - arcMin)/(arcMax - arcMin);
                b = (b- arcMin)/(arcMax - arcMin);
            }
            aMulb += a*b;
            aMula += a*a;
            bMulb += b*b;
        }
        double result = aMulb / (Math.sqrt(aMula) * Math.sqrt(bMulb));
        return result;
    }

}
