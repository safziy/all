package com.safziy.service;

import com.safziy.controller.response.WxMaLogin;

public interface LoginService {
    /**
     * 微信小程序登录
     *
     * @param openId 微信小程序openId
     */
    WxMaLogin wxMaLogin(String openId);
}
