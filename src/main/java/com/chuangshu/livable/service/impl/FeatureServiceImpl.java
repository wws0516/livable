package com.chuangshu.livable.service.impl;

import com.chuangshu.livable.base.service.impl.BaseServiceImpl;
import com.chuangshu.livable.entity.Feature;
import com.chuangshu.livable.mapper.FeatureMapper;
import com.chuangshu.livable.service.FeatureService;
import org.springframework.stereotype.Service;

@Service
public class FeatureServiceImpl extends BaseServiceImpl<FeatureMapper, Feature> implements FeatureService {
}
