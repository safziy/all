package com.safziy.controller.base;


import com.auth0.jwt.interfaces.Claim;
import com.safziy.constants.WebConstants;
import com.safziy.exception.LoginException;
import com.safziy.utils.JWTUtil;
import com.safziy.utils.WebUtil;
import org.apache.logging.log4j.util.Strings;

import java.time.Instant;
import java.util.Map;

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
        String token = WebUtil.getHeader(WebConstants.Token);
        if (Strings.isBlank(token)) {
            throw  new LoginException("无效的登录");
        }
        try {
            Map<String, Claim> tokenInfo = JWTUtil.getTokenInfo(token);
            return tokenInfo.get(WebConstants.LoginUserId).asInt();
        } catch (Exception e) {
            throw  new LoginException("无效的登录");
        }
    }

}
