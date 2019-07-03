package com.chuangshu.livable.service.impl;

import com.chuangshu.livable.base.service.impl.BaseServiceImpl;
import com.chuangshu.livable.entity.OpinionCount;
import com.chuangshu.livable.mapper.OpinionCountMapper;
import com.chuangshu.livable.service.OpinionCountService;
import org.springframework.stereotype.Service;

@Service
public class OpinionCountServiceImpl extends BaseServiceImpl<OpinionCountMapper, OpinionCount> implements OpinionCountService {
}
