CREATE TABLE `t_user`
(
    `id`         int(11)   NOT NULL AUTO_INCREMENT,
    `name`       varchar(255) COMMENT '用户名',
    `status`     tinyint(1)         DEFAULT '1' COMMENT '状态：1正常，0禁用',
    `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='用户表';

CREATE TABLE `t_wx_user`
(
    `id`         int(11)      NOT NULL AUTO_INCREMENT,
    `user_id`    int(11)      NOT NULL COMMENT '用户id',
    `union_id`   varchar(128) COMMENT '微信开发平台union_id',
    `open_id`    varchar(128) NOT NULL COMMENT '微信open_id',
    `nick_name`  varchar(256) COMMENT '昵称',
    `gender`     varchar(8) COMMENT '性别',
    `language`   varchar(32) COMMENT '语言',
    `city`       varchar(128) COMMENT '城市',
    `province`   varchar(128) COMMENT '省份',
    `country`    varchar(128) COMMENT '国家',
    `avatarUrl`  varchar(256) COMMENT '头像地址',
    `created_at` timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    KEY `key_union_id` (`union_id`),
    UNIQUE KEY `key_open_id` (`open_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='微信用户表';

CREATE TABLE `t_user_sudoku`
(
    `id`         int(11)   NOT NULL AUTO_INCREMENT,
    `user_id`    int(11)   NOT NULL COMMENT '用户id',
    `difficulty` int(11) COMMENT '难度',
    `level`      int(11) COMMENT '关卡',
    `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `key_user_difficulty` (`user_id`, `difficulty`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='用户数独表';

CREATE TABLE `t_user_sudoku_history`
(
    `id`             int(11)   NOT NULL AUTO_INCREMENT,
    `user_id`        int(11)   NOT NULL COMMENT '用户id',
    `difficulty`     int(11)   NOT NULL COMMENT '难度',
    `level`          int(11)   NOT NULL COMMENT '关卡',
    `best_cost_time` int(11) COMMENT '最佳耗时',
    `created_at`     timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at`     timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `key_user_difficulty_level` (`user_id`, `difficulty`, `level`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='用户数独历史记录';