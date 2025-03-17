-- database social_x
DROP TABLE IF EXISTS `config`;

CREATE TABLE `config` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `gmt_create` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'create time',
  `gmt_modified` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'modified time',
  `deleted_at` BIGINT NOT NULL DEFAULT '0' COMMENT 'deleted at',
  `config_key` VARCHAR(255) NOT NULL COMMENT 'config key',
  `config_value` TEXT NOT NULL COMMENT 'config value',
  `config_type` VARCHAR(32) NOT NULL DEFAULT 'STRING' COMMENT 'STRING, JSON, INTEGER, BOOLEAN, etc.',
  `version` INT NOT NULL DEFAULT '0' COMMENT 'version',
  `config_desc` VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'config description',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_config_key_deleted_at` (`config_key`,`deleted_at`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='project config';

DROP TABLE IF EXISTS `operation_log`;

CREATE TABLE `operation_log` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `gmt_create` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'create time',
    `gmt_modified` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'modified time',
    `extra_data` JSON COMMENT 'extra data',
    `operation_type` VARCHAR(64) NOT NULL COMMENT 'operation type',
    `operator_id` BIGINT NOT NULL COMMENT 'operator id',
    `operator_name` VARCHAR(64) NOT NULL COMMENT 'operator name',
    PRIMARY KEY (`id`),
    KEY `idx_operator_id_type` (`operator_id`, `operation_type`)
) ENGINE = InnoDB CHARSET = utf8mb4 COMMENT 'operation log';