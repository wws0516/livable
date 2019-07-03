package com.chuangshu.livable.service.impl;

import com.chuangshu.livable.base.service.impl.BaseServiceImpl;
import com.chuangshu.livable.entity.PersonalInformation;
import com.chuangshu.livable.mapper.PersonalInformationMapper;
import com.chuangshu.livable.service.PersonalInformationService;
import org.springframework.stereotype.Service;

@Service
public class PersonalInformationServiceImpl extends BaseServiceImpl<PersonalInformationMapper, PersonalInformation> implements PersonalInformationService {
}
