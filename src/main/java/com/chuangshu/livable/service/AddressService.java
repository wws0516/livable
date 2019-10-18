package com.chuangshu.livable.service;

import com.chuangshu.livable.base.service.BaseService;
import com.chuangshu.livable.dto.AddressDTO;
import com.chuangshu.livable.entity.Address;
import com.chuangshu.livable.entity.BaiduMapLocation;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AddressService extends BaseService<Address> {

    public List<AddressDTO> findAllByLevel(String level);

    public AddressDTO findByNameAndLevel(String name, String level);

    public List<AddressDTO> findByNameAndBelongTo(String name, String belongTo);

    public List<AddressDTO> findAllByLevelAndBelongTo(String level, String belongTo);

    public BaiduMapLocation getBaiduMapLocation(String city, String address);
}
