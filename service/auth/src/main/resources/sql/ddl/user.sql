CREATE TABLE user (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(255),
    account VARCHAR(255) UNIQUE,
    password VARCHAR(255),
    avatar VARCHAR(255),
    email VARCHAR(255),
    phone VARCHAR(255),
    tenantId VARCHAR(255),
    create_user VARCHAR(255),
    update_user VARCHAR(255),
    create_time DATETIME,
    update_time DATETIME,
    is_deleted tinyint,
    INDEX idx_tenantId (tenantId),
    INDEX idx_account (account)
);
