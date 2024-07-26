CREATE TABLE `tenant_user` (
                               `id` bigint NOT NULL AUTO_INCREMENT COMMENT '唯一标识',
                               `tenant_id` bigint NOT NULL COMMENT '租户唯一标识 建表需增加索引',
                               `create_user` bigint DEFAULT NULL COMMENT '创建人',
                               `update_user` bigint DEFAULT NULL COMMENT '更新人',
                               `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                               `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                               `deleted` tinyint DEFAULT '0' COMMENT '逻辑删除 建表需增加索引',
                               `tenant_name` varchar(255) NOT NULL COMMENT '租户名称',
                               `user_id` bigint NOT NULL COMMENT '用户唯一标识',
                               `user_name` varchar(255) DEFAULT NULL COMMENT '用户名',
                               PRIMARY KEY (`id`),
                               KEY `idx_tenant_id` (`tenant_id`),
                               KEY `idx_deleted` (`deleted`),
                               KEY `idx_user_id` (`user_id`) USING BTREE
) ENGINE=InnoDB COMMENT='租户用户表';