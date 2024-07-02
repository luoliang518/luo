package com.luo.auth.infrastructure.config.mybatis;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.luo.common.context.tenant.TenantContext;
import com.luo.common.context.tenant.TenantContextHolder;
import com.luo.common.context.user.UserContext;
import com.luo.common.context.user.UserContextHolder;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @Description
 * @Author luoliang
 * @Date 2024/6/21
 */
@Component
public class MybatisPlusMetaObjectHandler implements MetaObjectHandler {
    public static final String FIELD_TENANT_ID = "tenantId";
    public static final String FIELD_CREATE_USER = "createUser";
    public static final String FIELD_UPDATE_USER = "updateUser";
    public static final String FIELD_CREATE_TIME = "createTime";
    public static final String FIELD_UPDATE_TIME = "updateTime";
    public static final String FIELD_DELETED = "deleted";
    @Override
    public void insertFill(MetaObject metaObject) {
        this.fillStrategy(metaObject, FIELD_CREATE_TIME, LocalDateTime.now());
        this.fillStrategy(metaObject, FIELD_UPDATE_TIME, LocalDateTime.now());
        UserContext userContext = UserContextHolder.get();
        this.fillStrategy(metaObject, FIELD_CREATE_USER, userContext.getUserId());
        this.fillStrategy(metaObject, FIELD_UPDATE_USER, userContext.getUserId());
        TenantContext tenantContext = TenantContextHolder.get();
        this.fillStrategy(metaObject, FIELD_TENANT_ID, tenantContext.getTenantId());
        this.fillStrategy(metaObject, FIELD_DELETED, 0);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.strictUpdateFill(metaObject, FIELD_UPDATE_TIME, LocalDateTime.class, LocalDateTime.now());
        UserContext userContext = UserContextHolder.get();
        this.fillStrategy(metaObject, FIELD_UPDATE_USER, userContext.getUserId());
    }
}
