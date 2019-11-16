package com.chuangshu.livable.service.search;

import com.chuangshu.livable.LivableApplicationTests;
import com.chuangshu.livable.entity.Feature;
import com.chuangshu.livable.entity.House;
import com.chuangshu.livable.service.FeatureService;
import com.chuangshu.livable.service.HouseService;
import com.chuangshu.livable.utils.esUtil.HouseIndexTemplate;
import org.junit.Test;
import org.modelmapper.*;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author: wws
 * @Date: 2019-11-06 14:43
 */

public class a extends LivableApplicationTests {

    @Autowired
    HouseService houseService;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    FeatureService featureService;


    @Test
    public void te() {

        HouseIndexTemplate houseIndexTemplate = null;
        House house = null;

        try {
            System.out.println(featureService);
            house = houseService.get(3);
            System.out.println(house);
        } catch (Exception e) {
            e.printStackTrace();
        }

        House finalHouse = house;

        Converter<Integer, Feature> toUppercase = new AbstractConverter<Integer, Feature>() {

            @Override
            protected Feature convert(Integer source) {
                Feature feature = null;
                try {
                    feature = featureService.get(finalHouse.getFeatureId());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return feature;
            }
        };

        Provider<Feature> personProvider = new AbstractProvider<Feature>() {
            public Feature get() {
                Feature feature = null;

                try {
                    feature = featureService.get(finalHouse.getFeatureId());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return feature;
            }
        };
        //创建自定义映射规则
        PropertyMap<House, HouseIndexTemplate> propertyMap = new PropertyMap<House, HouseIndexTemplate>() {
            @Override
            protected void configure() {
                using(toUppercase).map(source.getFeatureId(),destination.getFeature());//使用自定义转换规则
                skip(destination.getSuggests());
            }
        };
        //添加映射器
        modelMapper.addMappings(propertyMap);
        modelMapper.validate();

        houseIndexTemplate = modelMapper.map(house, HouseIndexTemplate.class);
        System.out.println(houseIndexTemplate);

    }
}
