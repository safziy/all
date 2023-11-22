package com.safziy.controller;

import com.safziy.constants.WebConstants;
import com.safziy.controller.base.Resp;
import com.safziy.controller.base.WebSupport;
import com.safziy.utils.JWTUtil;
import com.safziy.utils.WebUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 *  控制层。
 *
 * @author safziy
 * @since 2023-11-08
 */
@RestController
@RequestMapping("/user")
public class UserController implements WebSupport {

    @PostMapping("mockLogin")
    public Resp<String> mockLogin() {
        Map<String, String> loginInfo = new HashMap<>();
        loginInfo.put(WebConstants.LoginUserId, String.valueOf(1));
        String token = JWTUtil.getToken(loginInfo);
        return success(token);
    }

}
