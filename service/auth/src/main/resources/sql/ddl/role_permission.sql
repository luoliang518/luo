CREATE TABLE role_permission (
                                 id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '唯一标识',
                                 tenant_id BIGINT NOT NULL COMMENT '租户唯一标识 建表需增加索引',
                                 create_user BIGINT COMMENT '创建人',
                                 update_user BIGINT COMMENT '更新人',
                                 create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                 update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                 deleted TINYINT DEFAULT 0 COMMENT '逻辑删除 建表需增加索引',
                                 role_id BIGINT COMMENT '角色id',
                                 role_name VARCHAR(50) COMMENT '角色名称',
                                 permission_id BIGINT COMMENT '权限id',
                                 permission_name VARCHAR(50) COMMENT '权限名称',
                                 permission_code VARCHAR(255) COMMENT '权限编码',
                                 INDEX idx_role_id (role_id),
                                 INDEX idx_permission_id (permission_id),
                                 INDEX idx_tenant_id (tenant_id),
                                 INDEX idx_deleted (deleted)
) COMMENT '角色权限关联表';
