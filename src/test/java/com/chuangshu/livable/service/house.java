package com.chuangshu.livable.service;

import com.chuangshu.livable.entity.Allocation;
import com.chuangshu.livable.entity.Feature;
import com.chuangshu.livable.entity.House;
import com.chuangshu.livable.entity.User;
import com.chuangshu.livable.redis.HouseRedisDTO;
import com.chuangshu.livable.service.redis.HouseRedisService;
import com.chuangshu.livable.service.redis.RedisService;
import com.chuangshu.livable.service.redis.UserRedisService;
import com.chuangshu.livable.utils.redisUtil.HouseRedisUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;


import java.lang.reflect.Field;
import java.util.*;

/**
 * @auther zhang
 * @ TO DO
 * @creatTime 2019/10/31 11:11
 **/

@RunWith(SpringRunner.class)
@SpringBootTest
public class house {

    @Autowired
    private HouseService houseService;

    @Autowired
    private AllocationService allocationService;

    @Autowired
    private FeatureService featureService;

    @Test
    public  void  test() throws Exception {
        List<House> houseList = houseService.findAll();
        for (House house : houseList) {
            System.out.println(house);
            //租金格式为数字
            String rent = house.getRent();
            String[] strings = rent.split("元/月");
            System.out.println(strings[0]);
            house.setRent(strings[0]);
            //修改合租方式
            //house.setRentWay(new Integer(new Random().nextInt(3)).toString());
            //修改电梯部分为无
            if (new Random().nextInt(2)==0) {
                house.setElevator("1");
            }else {
                house.setElevator("0");
            }
            //修改车位部分为无
            if (new Random().nextInt(2)==0) {
                house.setCarport("0");
            }else {
                house.setCarport("1");
            }
            //修改水电费
            if (new Random().nextInt(2)==0) {
                house.setEnergyCharge("0");

            }else {
                house.setEnergyCharge("1");
            }
            if (new Random().nextInt(2)==0) {
                house.setWaterCharge("0");

            }else {
                house.setWaterCharge("1");
            }

            //修改面积格式
            String acreage = house.getAcreage();
            String[] split = acreage.split("㎡");
            house.setAcreage(split[0]);
            //修改房屋配置
            String layout = house.getLayout();
            house.setAllocationId(house.getHouseId());
            house.setFeatureId(house.getHouseId());
            house.setLayout(null);
            System.out.println(house);
            houseService.update(house);
        }


    }

    @Test
    public  void test2() throws Exception {
        House house = houseService.get(3);
        System.out.println(house);
        //租金格式为数字
        String rent = house.getRent();
        String[] strings = rent.split("元/月");
        System.out.println(strings[0]);
        house.setRent(strings[0]);
        //修改合租方式
        //house.setRentWay(new Integer(new Random().nextInt(3)).toString());
        //修改电梯部分为无
        if (new Random().nextInt(2)==0) {
            house.setElevator("1");
        }else {
            house.setElevator("0");
        }
        //修改车位部分为无
        if (new Random().nextInt(2)==0) {
            house.setCarport("0");
        }else {
            house.setCarport("1");
        }
        //修改水电费
        if (new Random().nextInt(2)==0) {
            house.setEnergyCharge("0");

        }else {
            house.setEnergyCharge("1");
        }
        if (new Random().nextInt(2)==0) {
            house.setWaterCharge("0");

        }else {
            house.setWaterCharge("1");
        }

        //修改面积格式
        String acreage = house.getAcreage();
        String[] split = acreage.split("㎡");
        house.setAcreage(split[0]);
        //修改房屋配置
        String layout = house.getLayout();
        house.setAllocationId(house.getHouseId());
        house.setLayout(null);
        System.out.println(house);
        houseService.update(house);
    }


    @Test
    public  void  test4() throws Exception {
        List<House> houseList = houseService.findAll();
        Random random = new Random();
        for (House house : houseList) {
            String introduction = house.getIntroduction();
            Feature feature = featureService.get(house.getHouseId());
            //Allocation allocation = allocationService.get(house.getHouseId());

            //独立卫浴
            if (introduction.matches(".*卫.*")) {
                feature.setIndependentBathroom(1);
            }else {
                if (random.nextInt(2)==0) {
                    feature.setIndependentBathroom(1);
                }else {
                    feature.setIndependentBathroom(0);
                }
            }
            //阳台
            if (introduction.matches(".*阳台.*")) {
                feature.setIndependentBalcony(1);
            }
            //智能锁
            if (random.nextInt(2)==0) {
                feature.setSmartSock(1);
            }else {
                feature.setSmartSock(0);
            }
            //可自行装修
            if (random.nextInt(2)==0) {
                feature.setSelfDecorating(1);
            }else {
                feature.setSelfDecorating(0);
            }
            //首次出租
            if (introduction.matches(".*新.*")) {
                feature.setFirstRent(1);
            }else {
                if (random.nextInt(2)==0) {
                    feature.setFirstRent(1);
                }else {
                    feature.setFirstRent(0);
                }
            }
            //拎包入住
            if (introduction.matches(".*拎包.*")) {
                feature.setFullyFurnished(1);
            }else {
                if (random.nextInt(2)==0) {
                    feature.setFullyFurnished(1);
                }else {
                    feature.setFullyFurnished(0);
                }
            }
            //地铁十分钟
            if (introduction.matches(".*站.*")) {
                feature.setNearbySubway(1);
            }
            //随时入住
            if (introduction.matches(".*随时.*住.*")) {
                feature.setAnyTimeToSee(1);
            }
            //随时看房
            if (introduction.matches(".*随时.*看.*")) {
                feature.setCheckInAtOnce(1);
            }



            featureService.update(feature);
            //allocationService.update(allocation);
        }
    }

    @Test
    public  void test5() throws Exception {
        List<House> houseList = houseService.findAll();
        Random random = new Random();
        for (House house : houseList) {
            String introduction = house.getIntroduction();
            Allocation allocation = allocationService.get(house.getAllocationId());
            allocation.setWifi(random.nextInt(2));
            allocation.setWaterHeater(random.nextInt(2));
            allocation.setWashingMachine(random.nextInt(2));
            allocation.setTv(random.nextInt(2));
            allocation.setRefrigerator(random.nextInt(2));
            allocation.setDesk(random.nextInt(2));
            allocation.setCooking(random.nextInt(2));
            allocation.setChest(random.nextInt(2));
            allocation.setBeds(random.nextInt(2));
            allocation.setAirCondition(random.nextInt(2));
            //家电齐全
            if (introduction.matches(".*家.*电.*")) {
                allocation.setTv(1);
                allocation.setRefrigerator(1);
                allocation.setWashingMachine(1);
                allocation.setAirCondition(1);
                allocation.setWaterHeater(1);
            }
            //家具齐全
            if (introduction.matches(".*家.*私.*")) {
                allocation.setBeds(1);
                allocation.setChest(1);
                allocation.setDesk(1);
                allocation.setCooking(1);
            }

            allocationService.update(allocation);
        }
    }
    @Test
    public  void test6() throws Exception {
        House house = houseService.get(3);
        Feature feature = featureService.get(3);
        Allocation allocation = allocationService.get(3);
        HouseRedisDTO houseRedisDTO = new HouseRedisDTO(house, feature, allocation);
        System.out.println(houseRedisDTO);
    }

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    //@Resource(name = "RedisServiceImpl",type = RedisServiceImpl.class)
    private RedisService redisService;

    @Test
    public void test7() throws Exception {
        List<House> all = houseService.findAll();
        for (House house : all) {
            Feature feature = featureService.get(house.getFeatureId());
            Allocation allocation = allocationService.get(house.getAllocationId());
            HouseRedisDTO houseRedisDTO = new HouseRedisDTO(house, feature, allocation);
            redisService.set("house-"+house.getHouseId(),houseRedisDTO);
        }
    }

    @Test
    public void tes8() throws Exception {
        Set<String> keys = redisTemplate.keys("house-" + "*");
        List<Object> objects = redisTemplate.opsForValue().multiGet(keys);

        for (Object object : objects) {
            System.out.println(object);
        }
        List tranfor = objects;
        List<HouseRedisDTO> houseRedisDTOList = tranfor;
        System.out.println(houseRedisDTOList);
    }

    @Test
    public void test9() throws IllegalAccessException {
        HouseRedisDTO houseRedisDTO = (HouseRedisDTO) redisTemplate.opsForValue().get("house-86");
        Field[] fields = houseRedisDTO.getClass().getDeclaredFields();
        for (Field field : fields) {
            System.out.println(field.getName());
            field.setAccessible(true);
            Object o = field.get(houseRedisDTO);
            field.set(houseRedisDTO,(Double)o+1);
        }
        System.out.println(houseRedisDTO);
    }

    @Test
    public void test10() throws IllegalAccessException {
        HouseRedisDTO houseRedisDTO = (HouseRedisDTO) redisTemplate.opsForValue().get("house-3");
        Field[] fields = houseRedisDTO.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            Double aDouble = (Double) field.get(houseRedisDTO);
            if (aDouble ==0) {
                aDouble += Math.random();
            }else if (aDouble == 1){
                aDouble -= Math.random();
            }
            field.set(houseRedisDTO,aDouble);
        }
        redisService.set("user-0",houseRedisDTO);
        HouseRedisDTO houseRedisDTO1 = (HouseRedisDTO) redisTemplate.opsForValue().get("house-4");
        Field[] fields2 = houseRedisDTO1.getClass().getDeclaredFields();
        for (Field field : fields2) {
            field.setAccessible(true);
            Double aDouble = (Double) field.get(houseRedisDTO1);
            if (aDouble ==0) {
                aDouble += Math.random();
            }else if (aDouble == 1){
                aDouble -= Math.random();
            }
            field.set(houseRedisDTO1,aDouble);
        }
        redisService.set("user-1",houseRedisDTO1);
    }

    @Test
    public void test11() throws IllegalAccessException {
        Set<String> keys = redisTemplate.keys("user-" + "*");
        List<Object> objects = redisTemplate.opsForValue().multiGet(keys);
        List tranfor = objects;
        List<HouseRedisDTO> houseRedisDTOList = tranfor;
        Date date1 = new Date();
        Map<String,Double> houserRedisDTOMap = new HashMap<>();
        houserRedisDTOMap.put("numOfLivingRoom",houseRedisDTOList.stream().mapToDouble(HouseRedisDTO::getNumOfLivingRoom).average().getAsDouble());
        houserRedisDTOMap.put("numOfRoom",houseRedisDTOList.stream().mapToDouble(HouseRedisDTO::getNumOfRoom).average().getAsDouble());
        houserRedisDTOMap.put("numOfToilet",houseRedisDTOList.stream().mapToDouble(HouseRedisDTO::getNumOfToilet).average().getAsDouble());
        houserRedisDTOMap.put("rent",houseRedisDTOList.stream().mapToDouble(HouseRedisDTO::getRent).average().getAsDouble());
        houserRedisDTOMap.put("rentType",houseRedisDTOList.stream().mapToDouble(HouseRedisDTO::getRentType).average().getAsDouble());
        houserRedisDTOMap.put("numberOfPeople",houseRedisDTOList.stream().mapToDouble(HouseRedisDTO::getNumberOfPeople).average().getAsDouble());
        houserRedisDTOMap.put("rentWay",houseRedisDTOList.stream().mapToDouble(HouseRedisDTO::getRentWay).average().getAsDouble());
        houserRedisDTOMap.put("elevator",houseRedisDTOList.stream().mapToDouble(HouseRedisDTO::getElevator).average().getAsDouble());
        houserRedisDTOMap.put("toward",houseRedisDTOList.stream().mapToDouble(HouseRedisDTO::getToward).average().getAsDouble());
        houserRedisDTOMap.put("carport",houseRedisDTOList.stream().mapToDouble(HouseRedisDTO::getCarport).average().getAsDouble());
        houserRedisDTOMap.put("energyCharge",houseRedisDTOList.stream().mapToDouble(HouseRedisDTO::getEnergyCharge).average().getAsDouble());
        houserRedisDTOMap.put("waterCharge",houseRedisDTOList.stream().mapToDouble(HouseRedisDTO::getWaterCharge).average().getAsDouble());
        houserRedisDTOMap.put("acreage",houseRedisDTOList.stream().mapToDouble(HouseRedisDTO::getAcreage).average().getAsDouble());
        houserRedisDTOMap.put("independentBathroom",houseRedisDTOList.stream().mapToDouble(HouseRedisDTO::getIndependentBathroom).average().getAsDouble());
        houserRedisDTOMap.put("independentBalcony",houseRedisDTOList.stream().mapToDouble(HouseRedisDTO::getIndependentBalcony).average().getAsDouble());
        houserRedisDTOMap.put("smartSock",houseRedisDTOList.stream().mapToDouble(HouseRedisDTO::getSmartSock).average().getAsDouble());
        houserRedisDTOMap.put("selfDecorating",houseRedisDTOList.stream().mapToDouble(HouseRedisDTO::getSelfDecorating).average().getAsDouble());
        houserRedisDTOMap.put("firstRent",houseRedisDTOList.stream().mapToDouble(HouseRedisDTO::getFirstRent).average().getAsDouble());
        houserRedisDTOMap.put("fullyFurnished",houseRedisDTOList.stream().mapToDouble(HouseRedisDTO::getFullyFurnished).average().getAsDouble());
        houserRedisDTOMap.put("nearbySubway",houseRedisDTOList.stream().mapToDouble(HouseRedisDTO::getNearbySubway).average().getAsDouble());
        houserRedisDTOMap.put("anyTimeToSee",houseRedisDTOList.stream().mapToDouble(HouseRedisDTO::getAnyTimeToSee).average().getAsDouble());
        houserRedisDTOMap.put("checkInAtOnce",houseRedisDTOList.stream().mapToDouble(HouseRedisDTO::getCheckInAtOnce).average().getAsDouble());
        houserRedisDTOMap.put("tv",houseRedisDTOList.stream().mapToDouble(HouseRedisDTO::getTv).average().getAsDouble());
        houserRedisDTOMap.put("refrigerator",houseRedisDTOList.stream().mapToDouble(HouseRedisDTO::getRefrigerator).average().getAsDouble());
        houserRedisDTOMap.put("washingMachine",houseRedisDTOList.stream().mapToDouble(HouseRedisDTO::getWashingMachine).average().getAsDouble());
        houserRedisDTOMap.put("airCondition",houseRedisDTOList.stream().mapToDouble(HouseRedisDTO::getAirCondition).average().getAsDouble());
        houserRedisDTOMap.put("wifi",houseRedisDTOList.stream().mapToDouble(HouseRedisDTO::getWifi).average().getAsDouble());
        houserRedisDTOMap.put("beds",houseRedisDTOList.stream().mapToDouble(HouseRedisDTO::getBeds).average().getAsDouble());
        houserRedisDTOMap.put("waterHeater",houseRedisDTOList.stream().mapToDouble(HouseRedisDTO::getWaterHeater).average().getAsDouble());
        houserRedisDTOMap.put("chest",houseRedisDTOList.stream().mapToDouble(HouseRedisDTO::getChest).average().getAsDouble());
        houserRedisDTOMap.put("desk",houseRedisDTOList.stream().mapToDouble(HouseRedisDTO::getDesk).average().getAsDouble());
        houserRedisDTOMap.put("cooking",houseRedisDTOList.stream().mapToDouble(HouseRedisDTO::getCooking).average().getAsDouble());
        Date date2 = new Date();
        System.out.println(houserRedisDTOMap);
        System.out.println("map时间");
        System.out.println(date2.getTime() - date1.getTime());
        Date data3 = new Date();
        Field[] fields = HouseRedisDTO.class.getDeclaredFields();
        Double[] newfields = new Double[fields.length];
        for (int i = 0; i < newfields.length; i++) {
            newfields[i] = 0d;
        }
        for (int i = 0; i < fields.length; i++) {

            for (HouseRedisDTO houseRedisDTO : houseRedisDTOList) {
                fields[i].setAccessible(true);
                Double aDouble = (Double) fields[i].get(houseRedisDTO);
                newfields[i] += aDouble;
            }
        }
        for (int i = 0; i < newfields.length; i++) {
            newfields[i] /=houseRedisDTOList.size();
        }
        Date data4 = new Date();
        System.out.println("数组时间");
        System.out.println(data4.getTime() - data3.getTime());
        HouseRedisDTO houseRedisDTO = new HouseRedisDTO();
        Field[] declaredFields = houseRedisDTO.getClass().getDeclaredFields();
        for (int i = 0; i < declaredFields.length; i++) {
            declaredFields[i].setAccessible(true);
            declaredFields[i].set(houseRedisDTO,newfields[i]);
        }
        System.out.println(houseRedisDTO);
    }

    @Test
    public void test12() throws Exception{
        HouseRedisDTO houseRedisDTO = (HouseRedisDTO) redisTemplate.opsForValue().get("house-5");
        HouseRedisDTO userRedisDTO = (HouseRedisDTO) redisTemplate.opsForValue().get("house-59");
        //System.out.println("house----"+houseRedisDTO);
        //System.out.println("user---"+userRedisDTO);
        Field[] houseFields = houseRedisDTO.getClass().getDeclaredFields();
        Field[] userFields = userRedisDTO.getClass().getDeclaredFields();
        Date start = new Date();
        for (int i = 0; i < userFields.length; i++) {
            userFields[i].setAccessible(true);
            houseFields[i].setAccessible(true);
            Double houseData = (Double) houseFields[i].get(houseRedisDTO);
            for (int a =0; a<=5;a++){
                Double userData = (Double) userFields[i].get(userRedisDTO);
                Double newFieldData = userData - 0.2 * (userData - houseData);
                houseFields[i].set(userRedisDTO , newFieldData);
                //System.out.println(houseFields[i].getName()+"------"+houseFields[i].get(userRedisDTO));
            }
        }
        Date end = new Date();
        System.out.println(end.getTime() - start.getTime());
    }


    @Test
    public void test13() throws Exception{
        HouseRedisDTO houseRedisDTOa = (HouseRedisDTO) redisTemplate.opsForValue().get("house-5");
        HouseRedisDTO houseRedisDTOb = (HouseRedisDTO) redisTemplate.opsForValue().get("house-145");
        Field[] houseFields = HouseRedisDTO.class.getDeclaredFields();
        Double aMulb = 0d;
        Double aMula = 0d;
        Double bMulb = 0d;
        System.out.println(houseFields.length);
        for (int i = 0; i < houseFields.length; i++) {
            houseFields[i].setAccessible(true);
            Double a = (Double) houseFields[i].get(houseRedisDTOa);
            System.out.println("a."+houseFields[i].getName()+"----"+a);
            Double b = (Double) houseFields[i].get(houseRedisDTOb);
            System.out.println("b."+houseFields[i].getName()+"----"+b);
            aMulb += a*b;
            aMula += a*a;
            bMulb += b*b;
        }
        double result = aMula / (Math.sqrt(aMula) * Math.sqrt(bMulb));
        System.out.println(result);

        System.out.println(HouseRedisUtil.cosDistance(houseRedisDTOb, houseRedisDTOa));
    }

    @Autowired
    private HouseRedisService houseRedisService;

    @Test
    public void test14() throws IllegalAccessException {
        Date start = new Date();
        List<HouseRedisDTO> houseDTOList = houseRedisService.getHouseDTOList();
        Double renmax = houseDTOList.stream().mapToDouble(HouseRedisDTO::getRent).max().getAsDouble();
        Double renmin = houseDTOList.stream().mapToDouble(HouseRedisDTO::getRent).min().getAsDouble();
        Double arcmax = houseDTOList.stream().mapToDouble(HouseRedisDTO::getAcreage).max().getAsDouble();
        Double arcmin = houseDTOList.stream().mapToDouble(HouseRedisDTO::getAcreage).min().getAsDouble();
        HouseRedisDTO houseRedisDTO = (HouseRedisDTO) houseRedisService.get("house-5");
        ArrayList<String> keys = new ArrayList<>(redisTemplate.keys("house-" + "*"));
        //HashMap<String, Double> distances = new HashMap<>();
        Map<String, Double> distances = new TreeMap<>();
        for (int i = 0; i < houseDTOList.size(); i++) {
            HouseRedisDTO redisDTO = houseDTOList.get(i);
            Double aDouble = HouseRedisUtil.NormailCosDistance(redisDTO,houseRedisDTO,renmax,renmin,arcmax,arcmin);
            distances.put(keys.get(i),aDouble);
        }
        Date end = new Date();
        System.out.println(end.getTime() - start.getTime());
        for (String s : distances.keySet()) {
            System.out.println(s+"--"+distances.get(s));
        }
        System.out.println("---------排序后-------");
        List<Map.Entry<String,Double>> list = new ArrayList<Map.Entry<String,Double>>(distances.entrySet());
        //排序
        Collections.sort(list, new Comparator<Map.Entry<String, Double>>(){
            public int compare(Map.Entry<String, Double> map1, Map.Entry<String,Double> map2) {
                return ((map2.getValue() - map1.getValue() == 0) ? 0 : (map2.getValue() - map1.getValue() > 0) ? 1 : -1);
            }
        });
        //输出
        for (Map.Entry<String, Double> entry : list) {
            System.out.println(entry.getKey() + "\t" + entry.getValue());
        }

    }


    @Test
    public void test15()throws  Exception{
        HouseRedisDTO houseRedisDTOa = (HouseRedisDTO) redisTemplate.opsForValue().get("house-5");
        HouseRedisDTO houseRedisDTOb = (HouseRedisDTO) redisTemplate.opsForValue().get("house-145");
        System.out.println("a--"+houseRedisDTOa);
        System.out.println("b--"+houseRedisDTOb);
        System.out.println(HouseRedisUtil.cosDistance(houseRedisDTOa, houseRedisDTOb));
        System.out.println(HouseRedisUtil.cosDistance(houseRedisDTOb, houseRedisDTOa));
    }

    @Autowired
    UserRedisService userRedisService;

    @Test
    public void test16()throws  Exception{
        User user = new User();
        user.setUserId("1");
        List<Integer> strings = userRedisService.userGetRecommend(user);
        System.out.println(strings);
    }

    @Test
    public void test17()throws  Exception{
        House house = houseService.get(14);
        System.out.println(house);
    }
}
