package com.chuangshu.livable.service.address;

import com.chuangshu.livable.LivableApplicationTests;
import com.chuangshu.livable.utils.baiduMapUtil.BaiduMapLocation;
import com.chuangshu.livable.service.AddressService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by 瓦力.
 */
public class AddressServiceTests extends LivableApplicationTests {
    @Autowired
    private AddressService addressService;

    @Test
    public void testGetMapLocation() {
        String city = "北京";
        String address = "北京市昌平区巩华家园1号楼2单元";
        BaiduMapLocation serviceResult = addressService.getBaiduMapLocation(city, address);

        Assert.assertTrue(serviceResult.getLongitude() > 0 );
        Assert.assertTrue(serviceResult.getLatitude() > 0 );

    }
}
