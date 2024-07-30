CREATE TABLE `user` (
                        `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '唯一标识',
                        `tenant_id` bigint(20) NOT NULL DEFAULT '1' COMMENT '租户唯一标识 建表需增加索引',
                        `create_user` bigint(20) DEFAULT NULL COMMENT '创建人',
                        `update_user` bigint(20) DEFAULT NULL COMMENT '更新人',
                        `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                        `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                        `deleted` tinyint(4) DEFAULT '0' COMMENT '逻辑删除 建表需增加索引',
                        `username` varchar(255) DEFAULT NULL COMMENT '用户名',
                        `account` varchar(255) DEFAULT NULL COMMENT '账号，唯一索引',
                        `password` varchar(255) DEFAULT NULL COMMENT '密码',
                        `avatar` varchar(255) DEFAULT NULL COMMENT '头像',
                        `email` varchar(255) DEFAULT NULL COMMENT '邮箱',
                        `phone` varchar(50) DEFAULT NULL COMMENT '手机号',
                        PRIMARY KEY (`id`),
                        UNIQUE KEY `account` (`account`),
                        KEY `idx_tenant_id` (`tenant_id`),
                        KEY `idx_deleted` (`deleted`)
) ENGINE=InnoDB COMMENT='用户表'
