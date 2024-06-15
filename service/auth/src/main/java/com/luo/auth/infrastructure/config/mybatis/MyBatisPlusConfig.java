package com.luo.auth.infrastructure.config.mybatis;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.*;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
public class MyBatisPlusConfig {
    private final CustomTenantHandler customTenantHandler;
    /**
     * 分页插件 性能分析插件
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();

        /*------------------------数据隔离插件------------------------*/
        // 租户插件
        TenantLineInnerInterceptor tenantInterceptor = new TenantLineInnerInterceptor();
        tenantInterceptor.setTenantLineHandler(customTenantHandler);
        interceptor.addInnerInterceptor(tenantInterceptor);
        // 数据权限插件
//        InnerInterceptor dataPermissionInterceptor = new DataPermissionInterceptor(new CustomDataPermissionHandler());
//        interceptor.addInnerInterceptor(dataPermissionInterceptor);


        /*------------------------数据规范插件------------------------*/
        // 添加分页插件
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor());
        // 阻止全表更新删除
        interceptor.addInnerInterceptor(new BlockAttackInnerInterceptor());
        // 添加非法SQL拦截器
        interceptor.addInnerInterceptor(new IllegalSQLInnerInterceptor());
        // 配置安全阈值，例如限制批量更新或插入的记录数不超过 500 条
        DataChangeRecorderInnerInterceptor dataChangeRecorderInnerInterceptor = new DataChangeRecorderInnerInterceptor();
        dataChangeRecorderInnerInterceptor.setBatchUpdateLimit(500);
        interceptor.addInnerInterceptor(dataChangeRecorderInnerInterceptor);

        return interceptor;
    }
}
