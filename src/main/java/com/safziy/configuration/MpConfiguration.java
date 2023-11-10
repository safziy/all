package com.safziy.configuration;


import com.safziy.configuration.config.WxMpProperties;
import com.safziy.wx.handler.WxMpLogHandler;
import com.safziy.wx.handler.WxMpSubscribeHandler;
import com.safziy.wx.handler.WxMpUnsubscribeHandler;
import com.safziy.wx.handler.WxMpViewHandler;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.config.impl.WxMpDefaultConfigImpl;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.stream.Collectors;

import static me.chanjar.weixin.common.api.WxConsts.EventType.SUBSCRIBE;
import static me.chanjar.weixin.common.api.WxConsts.EventType.UNSUBSCRIBE;
import static me.chanjar.weixin.common.api.WxConsts.MenuButtonType.VIEW;
import static me.chanjar.weixin.common.api.WxConsts.XmlMsgType.EVENT;

@Configuration
@EnableConfigurationProperties(WxMpProperties.class)
public class MpConfiguration {

    @Bean
    public WxMpService wxMpService(WxMpProperties wxMpProperties) {
        // 注册Service
        WxMpService wxMpService = new WxMpServiceImpl();
        wxMpService.setMultiConfigStorages(wxMpProperties.getConfigs().stream().map(prop -> {
            // redis存储
//        WxMpDefaultConfigImpl config = new WxMpRedisConfigImpl(new RedisTemplateWxRedisOps(redisTemplate));
            WxMpDefaultConfigImpl config = new WxMpDefaultConfigImpl();
            config.setAppId(prop.getAppId());
            config.setSecret(prop.getSecret());
            config.setToken(prop.getToken());
            config.setAesKey(prop.getAesKey());
            return config;
        }).collect(Collectors.toMap(WxMpDefaultConfigImpl::getAppId, c -> c)));
        return wxMpService;
    }

    @Bean
    public WxMpMessageRouter wxMpMessageRouter(WxMpService wxMpService) {
        WxMpMessageRouter router = new WxMpMessageRouter(wxMpService);
        // 记录所有事件的日志 （异步执行）
        router.rule().handler(new WxMpLogHandler()).next();

//        // 接收客服会话管理事件
//        router.rule().async(false).msgType(EVENT).event(KF_CREATE_SESSION)
//                .handler(this.kfSessionHandler).end();
//        router.rule().async(false).msgType(EVENT).event(KF_CLOSE_SESSION)
//                .handler(this.kfSessionHandler).end();
//        router.rule().async(false).msgType(EVENT).event(KF_SWITCH_SESSION)
//                .handler(this.kfSessionHandler).end();
//        // 门店审核事件
//        router.rule().async(false).msgType(EVENT).event(POI_CHECK_NOTIFY).handler(this.storeCheckNotifyHandler).end();
//        // 自定义菜单事件
//        router.rule().async(false).msgType(EVENT).event(EventType.CLICK).handler(this.menuHandler).end();
        // 点击菜单连接事件
        router.rule().async(false).msgType(EVENT).event(VIEW).handler(new WxMpViewHandler()).end();
        // 关注事件
        router.rule().async(false).msgType(EVENT).event(SUBSCRIBE).handler(new WxMpSubscribeHandler()).end();
        // 取消关注事件
        router.rule().async(false).msgType(EVENT).event(UNSUBSCRIBE).handler(new WxMpUnsubscribeHandler()).end();
//        // 上报地理位置事件
//        router.rule().async(false).msgType(EVENT).event(EventType.LOCATION).handler(this.locationHandler).end();
//        // 接收地理位置消息
//        router.rule().async(false).msgType(XmlMsgType.LOCATION).handler(this.locationHandler).end();
//        // 扫码事件
//        router.rule().async(false).msgType(EVENT).event(EventType.SCAN).handler(this.scanHandler).end();

        // 默认其他的事件
//        router.rule().async(false).handler(new DefaultHandler()).end();
        return router;
    }
}
