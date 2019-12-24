package com.chuangshu.livable.service.impl;

import com.chuangshu.livable.base.service.impl.BaseServiceImpl;
import com.chuangshu.livable.entity.Allocation;
import com.chuangshu.livable.entity.UserRole;
import com.chuangshu.livable.mapper.AllocationMapper;
import com.chuangshu.livable.mapper.UserRoleMapper;
import com.chuangshu.livable.service.AllocationService;
import com.chuangshu.livable.service.UserRoleService;
import org.springframework.stereotype.Service;

@Service
public class UserRoleServiceImpl extends BaseServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {
}
