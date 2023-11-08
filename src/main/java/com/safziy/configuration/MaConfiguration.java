package com.safziy.configuration;


import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.impl.WxMaServiceImpl;
import cn.binarywang.wx.miniapp.config.impl.WxMaDefaultConfigImpl;
import cn.binarywang.wx.miniapp.message.WxMaMessageRouter;
import com.safziy.configuration.config.WxMaProperties;
import com.safziy.handler.WxMaLogHandler;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.stream.Collectors;


@Configuration
@EnableConfigurationProperties(WxMaProperties.class)
public class MaConfiguration {

    @Bean
    public WxMaService wxMaService(WxMaProperties wxMaProperties) {
        // 注册Service
        WxMaService wxMaService = new WxMaServiceImpl();
        wxMaService.setMultiConfigs(wxMaProperties.getConfigs().stream().map(prop -> {
            WxMaDefaultConfigImpl config = new WxMaDefaultConfigImpl();
            config.setAppid(prop.getAppId());
            config.setSecret(prop.getSecret());
            config.setToken(prop.getToken());
            config.setAesKey(prop.getAesKey());
            config.setMsgDataFormat(prop.getMsgDataFormat());
            return config;
        }).collect(Collectors.toMap(WxMaDefaultConfigImpl::getAppid, c -> c)));
        return wxMaService;
    }

    @Bean
    public WxMaMessageRouter wxMaMessageRouter(WxMaService wxMaService) {
        WxMaMessageRouter router = new WxMaMessageRouter(wxMaService);
        // 记录所有事件的日志 （异步执行）
        router.rule().handler(new WxMaLogHandler()).next();
        return router;
    }
}
