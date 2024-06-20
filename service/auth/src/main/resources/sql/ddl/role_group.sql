CREATE TABLE role_group_po (
    id BIGINT PRIMARY KEY,
    tenant_id BIGINT,
    create_user BIGINT,
    update_user BIGINT,
    create_time TIMESTAMP,
    update_time TIMESTAMP,
    deleted TINYINT,
    role_group_name VARCHAR(255),
    role_ids VARCHAR(255)
);