package com.safziy.controller.base;

import com.safziy.constants.ErrCode;
import com.safziy.constants.WebConstants;
import com.safziy.utils.JsonUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
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
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute(WebConstants.LoginUserId);
        if (userId != 0) {
            return true;
        }
        Resp<Object> result = error(ErrCode.LoginInvalid, "");
        response.getWriter().write(JsonUtil.toJson(result));
        response.getWriter().flush();
        return false;
    }
}
