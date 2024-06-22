package com.luo.auth.application.user.dto.vo;

import com.luo.auth.domain.userAggregate.entity.Token;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Description
 * @Author luoliang
 * @Date 2024/6/14
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserVO {
    private Long userId;
    private String account;
    private String username;
    private Token token;
    private List<TenantVo> tenantVos;
}
