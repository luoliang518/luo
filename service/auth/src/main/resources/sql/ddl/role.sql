CREATE TABLE role (
                      id BIGINT PRIMARY KEY,
                      tenant_id BIGINT,
                      create_user BIGINT,
                      update_user BIGINT,
                      create_time TIMESTAMP,
                      update_time TIMESTAMP,
                      deleted TINYINT,
                      role_group_id BIGINT,
                      role_name VARCHAR(255)
);