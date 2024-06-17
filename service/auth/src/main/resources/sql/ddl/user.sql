CREATE TABLE `user` (
                        `id` BIGINT NOT NULL AUTO_INCREMENT,
                        `username` VARCHAR(255) DEFAULT NULL,
                        `account` VARCHAR(255) DEFAULT NULL,
                        `password` VARCHAR(255) DEFAULT NULL,
                        `avatar` VARCHAR(255) DEFAULT NULL,
                        `email` VARCHAR(255) DEFAULT NULL,
                        `phone` VARCHAR(255) DEFAULT NULL,
                        `tenant_id` VARCHAR(255) DEFAULT NULL,
                        `create_user` VARCHAR(255) DEFAULT NULL,
                        `update_user` VARCHAR(255) DEFAULT NULL,
                        `create_time` DATETIME DEFAULT NULL,
                        `update_time` DATETIME DEFAULT NULL,
                        `deleted` TINYINT DEFAULT NULL,
                        PRIMARY KEY (`id`),
                        UNIQUE KEY `account` (`account`),
                        KEY `idx_tenantId` (`tenant_id`),
                        KEY `idx_account` (`account`),
                        KEY `idx_deleted` (`deleted`) USING BTREE
) ENGINE=INNODB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8mb4