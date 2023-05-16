package com.luo.spring.service.impl;

import com.luo.spring.bean.LuoScope;
import com.luo.spring.component.LuoComponent;
import com.luo.spring.service.UserBaseService;

@LuoComponent
@LuoScope("prototype")
public class UserBaseServiceImpl implements UserBaseService {
    public UserBaseServiceImpl() {
    }
}
