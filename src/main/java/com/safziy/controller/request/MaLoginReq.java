package com.safziy.controller.request;

import lombok.Data;

@Data
public class MaLoginReq {
    /**
     * 微信登录code
     */
    private String code;
}
