package com.chuangshu.livable.service.impl;

import com.chuangshu.livable.base.service.impl.BaseServiceImpl;
import com.chuangshu.livable.entity.Allocation;
import com.chuangshu.livable.mapper.AllocationMapper;
import com.chuangshu.livable.service.AllocationService;
import org.springframework.stereotype.Service;

@Service
public class AllocationServiceImpl extends BaseServiceImpl<AllocationMapper, Allocation> implements AllocationService {
}
