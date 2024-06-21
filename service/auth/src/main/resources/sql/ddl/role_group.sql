CREATE TABLE role_group (
                            id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '唯一标识',
                            tenant_id BIGINT NOT NULL COMMENT '租户唯一标识 建表需增加索引',
                            create_user BIGINT COMMENT '创建人',
                            update_user BIGINT COMMENT '更新人',
                            create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                            update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                            deleted TINYINT DEFAULT 0 COMMENT '逻辑删除 建表需增加索引',
                            roel_group_code VARCHAR(255) COMMENT '角色组代码',
                            role_group_name VARCHAR(255) COMMENT '角色组名称',
                            role_ids TEXT COMMENT '角色ID集合，以逗号分隔',
                            INDEX idx_tenant_id (tenant_id),
                            INDEX idx_deleted (deleted)
) COMMENT '角色组表';
