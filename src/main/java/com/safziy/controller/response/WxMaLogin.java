package com.safziy.controller.response;

import lombok.Data;

@Data
public class WxMaLogin {
    /**
     * userId 用户Id
     */
    private Integer userId;
    /**
     * openId 微信openId
     */
    private String openId;
}
