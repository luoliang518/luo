package com.luo.auth.application.user.dto.vo;

import com.luo.auth.domain.utilAggergate.entity.Token;
import com.luo.auth.domain.userAggregate.entity.User;
import lombok.Data;

import java.util.List;

/**
 * @Description
 * @Author luoliang
 * @Date 2024/6/14
 */
@Data
public class UserVO {
    private Long userId;
    private String account;
    private String userName;
    private Token token;
    private List<UserTenantVo> userTenantVos;
    public UserVO(User user) {
        this.userId = user.getUserId();
        this.account = user.getAccount();
        this.userName = user.getUsername();
        this.token = user.getToken();
    }
}
