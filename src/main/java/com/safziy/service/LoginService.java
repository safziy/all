package com.safziy.service;

import com.safziy.entity.WxUser;

public interface LoginService {
    /**
     * 微信小程序登录
     *
     * @param openId 微信小程序openId
     */
    WxUser wxMaLogin(String openId);
}
