CREATE TABLE `t_user`
(
    `id`         int(11)      NOT NULL AUTO_INCREMENT,
    `name`       varchar(255) NOT NULL COMMENT '用户名',
    `status`     tinyint(1)   NOT NULL DEFAULT '1' COMMENT '状态：1正常，0禁用',
    `created_at` timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='用户表';

CREATE TABLE `t_wx_user`
(
    `id`         int(11)      NOT NULL AUTO_INCREMENT,
    `user_id`    int(11)      NOT NULL COMMENT '用户id',
    `union_id`   varchar(128) NULL COMMENT '微信开发平台union_id',
    `open_id`    varchar(128) NOT NULL COMMENT '微信open_id',
    `nick_name`  varchar(256) NOT NULL COMMENT '昵称',
    `gender`     varchar(8)   NOT NULL COMMENT '性别',
    `language`   varchar(32)  NOT NULL COMMENT '语言',
    `city`       varchar(128) NOT NULL COMMENT '城市',
    `province`   varchar(128) NOT NULL COMMENT '省份',
    `country`    varchar(128) NOT NULL COMMENT '国家',
    `avatarUrl`  varchar(256) NOT NULL COMMENT '头像地址',
    `created_at` timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    KEY `key_union_id` (`union_id`),
    UNIQUE KEY `key_open_id` (`open_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='微信用户表';