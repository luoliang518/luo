package com.luo.auth.application.user.service;

import com.luo.auth.application.user.dto.query.UserQuery;
import com.luo.auth.application.user.dto.vo.UserVO;

/**
 * @Description
 * @Author luoliang
 * @Date 2024/6/14
 */
public interface UserAppService {
    UserVO userLogin(UserQuery userQuery);
}
