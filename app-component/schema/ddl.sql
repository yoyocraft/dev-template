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

CREATE TABLE `local_message` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `gmt_create` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'create time',
    `gmt_modified` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'modified time',
    `deleted_at` BIGINT NOT NULL DEFAULT '0' COMMENT 'deleted at',
    `req_snapshot` JSON NOT NULL COMMENT '请求快照参数json',
    `status` VARCHAR(16) NOT NULL COMMENT '状态 INIT, FAIL, SUCCESS',
    `next_retry_time` BIGINT NOT NULL COMMENT '下一次重试的时间',
    `retry_times` INT NOT NULL DEFAULT 0 COMMENT '已经重试的次数',
    `max_retry_times` INT NOT NULL COMMENT '最大重试次数',
    `fail_reason` TEXT COMMENT '执行失败的信息',
    `biz_code` VARCHAR(64) NOT NULL COMMENT '业务code',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='本地消息表';