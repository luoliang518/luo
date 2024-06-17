CREATE TABLE permission (
                            id BIGINT PRIMARY KEY,
                            tenant_id BIGINT,
                            create_user BIGINT,
                            update_user BIGINT,
                            create_time TIMESTAMP,
                            update_time TIMESTAMP,
                            deleted TINYINT,
                            role_id BIGINT,
                            permission_par_id BIGINT,
                            permission_level VARCHAR(50),
                            permission_code VARCHAR(255),
                            permission_name VARCHAR(255),
                            permission_desc VARCHAR(255)
);
