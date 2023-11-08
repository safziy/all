package com.safziy.handler;

import me.chanjar.weixin.mp.api.WxMpMessageHandler;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;

public abstract class AbstractWxMpHandler implements WxMpMessageHandler {
    public static final WxMpXmlOutMessage EMPTY = null;
}
