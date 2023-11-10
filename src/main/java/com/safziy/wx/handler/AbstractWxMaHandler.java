package com.safziy.wx.handler;

import cn.binarywang.wx.miniapp.message.WxMaMessageHandler;
import cn.binarywang.wx.miniapp.message.WxMaXmlOutMessage;

public abstract class AbstractWxMaHandler implements WxMaMessageHandler {
    public static final WxMaXmlOutMessage EMPTY = null;
}
