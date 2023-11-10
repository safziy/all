package com.safziy.controller.base;


import com.safziy.constants.WebConstants;
import com.safziy.utils.WebUtil;
import jakarta.servlet.http.HttpSession;

import java.time.Instant;

public interface WebSupport {

    public static final String SUCCESS = "success";
    public static final String OK = "ok";

    default <T> Resp<T> success(T t) {
        return buildResp(SUCCESS, OK, t);
    }

    default <T> Resp<T> error(String code, String message) {
        return buildResp(code, message, null);
    }

    default <T> Resp<T> error(String code, String message, T t) {
        return buildResp(code, message, t);
    }

    default <T> Resp<T> buildResp(String code, String message, T t) {
        Resp<T> resp = new Resp<>();
        resp.setCode(code);
        resp.setMessage(message);
        resp.setTimestamp(Instant.now().getEpochSecond());
        resp.setSeqId("TODO ...");
        resp.setData(t);
        return resp;
    }

    default Integer getLoginUser() {
        HttpSession session = WebUtil.getSession();
        return (Integer) session.getAttribute(WebConstants.LoginUserId);
    }

}
