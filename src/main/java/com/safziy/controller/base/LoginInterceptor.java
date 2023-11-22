package com.safziy.controller.base;

import com.safziy.constants.ErrCode;
import com.safziy.constants.WebConstants;
import com.safziy.utils.JWTUtil;
import com.safziy.utils.JsonUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 登录拦截器
 */
@Slf4j
@Component
public class LoginInterceptor implements HandlerInterceptor, WebSupport {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader(WebConstants.Token);
        if (Strings.isBlank(token)) {
            Resp<Object> result = error(ErrCode.LoginInvalid, "");
            response.getWriter().write(JsonUtil.toJson(result));
            response.getWriter().flush();
            return false;
        }
        if (JWTUtil.verify(token)) {
            return true;
        }
        Resp<Object> result = error(ErrCode.LoginInvalid, "");
        response.getWriter().write(JsonUtil.toJson(result));
        response.getWriter().flush();
        return false;
    }
}
