package com.safziy.wx.handler;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaMessage;
import cn.binarywang.wx.miniapp.message.WxMaXmlOutMessage;
import com.safziy.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;

import java.util.Map;


@Slf4j
public class WxMaLogHandler extends AbstractWxMaHandler {
    @Override
    public WxMaXmlOutMessage handle(WxMaMessage message, Map<String, Object> context,
                                    WxMaService service, WxSessionManager sessionManager) throws WxErrorException {
        log.info("\n接收到请求消息，内容：{}", JsonUtil.toJson(message));
//        service.getMsgService().sendKefuMsg(WxMaKefuMessage.newTextBuilder().content("收到信息为：" + wxMessage.toJson())
//                .toUser(wxMessage.getFromUser()).build());
        return EMPTY;
    }
}
