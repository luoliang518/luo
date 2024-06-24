CREATE TABLE tenant(
                           id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '唯一标识',
                           tenant_id BIGINT NOT NULL COMMENT '租户唯一标识 建表需增加索引',
                           create_user BIGINT COMMENT '创建人',
                           update_user BIGINT COMMENT '更新人',
                           create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                           update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                           deleted TINYINT DEFAULT 0 COMMENT '逻辑删除 建表需增加索引',
                           tenant_name VARCHAR(255) NOT NULL COMMENT '租户名称',
                           contact_name VARCHAR(255) COMMENT '联系人姓名',
                           contact_phone VARCHAR(50) COMMENT '联系电话',
                           contact_email VARCHAR(255) COMMENT '联系邮箱',
                           cert VARCHAR(255) COMMENT '证书',
                           cert_issue_date DATE COMMENT '证书签发日期',
                           cert_expiry_date DATE COMMENT '证书到期日期',
                           cert_status VARCHAR(50) COMMENT '证书状态',
                           INDEX idx_tenant_id (tenant_id),
                           INDEX idx_deleted (deleted)
) COMMENT '租户信息表';
