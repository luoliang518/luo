CREATE TABLE user (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(255),
    account VARCHAR(255) UNIQUE,
    password VARCHAR(255),
    avatar VARCHAR(255),
    email VARCHAR(255),
    phone VARCHAR(255),
    tenantId VARCHAR(255),
    createUser VARCHAR(255),
    updateUser VARCHAR(255),
    createTime DATETIME,
    updateTime DATETIME,
    INDEX idx_tenantId (tenantId),
    INDEX idx_account (account)
);
