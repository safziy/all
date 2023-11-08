package com.safziy.configuration.config;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@Getter
@Setter
@ToString
@ConfigurationProperties(prefix = "wx.ma")
public class WxMaProperties {
    /**
     * 默认公众号
     */
    private String defaultAppId;
    /**
     * 公众号配置
     */
    private List<MpConfig> configs;

    @Data
    public static class MpConfig {
        /**
         * 公众号的唯一标识
         */
        private String appId;
        /**
         * 公众号的开发者密钥
         */
        private String secret;
        /**
         * 令牌
         */
        private String token;
        /**
         * 消息加解密密钥
         */
        private String aesKey;
        /**
         *
         */
        private String msgDataFormat;
    }
}
