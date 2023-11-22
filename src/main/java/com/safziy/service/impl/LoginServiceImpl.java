package com.safziy.service.impl;

import com.safziy.constants.WebConstants;
import com.safziy.controller.response.WxMaLogin;
import com.safziy.entity.User;
import com.safziy.entity.WxUser;
import com.safziy.mapper.UserMapper;
import com.safziy.mapper.WxUserMapper;
import com.safziy.service.LoginService;
import com.safziy.utils.JWTUtil;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class LoginServiceImpl implements LoginService {

    @Resource
    private WxUserMapper wxUserMapper;
    @Resource
    private UserMapper userMapper;

    @Override
    public WxMaLogin wxMaLogin(String openId) {
        WxUser wxUser = wxUserMapper.findByOpenId(openId);
        if (wxUser == null) {
            // 新用户
            User user = new User();
            int rowAffected = userMapper.insert(user);
            if (rowAffected <= 0 ) {
                throw new RuntimeException();
            }
            wxUser = new WxUser();
            wxUser.setOpenId(openId);
            wxUser.setUserId(user.getId());
            rowAffected = wxUserMapper.insert(wxUser);
            if (rowAffected <= 0 ) {
                throw new RuntimeException();
            }
        }
        Map<String, String> loginInfo = new HashMap<>();
        loginInfo.put(WebConstants.LoginUserId, wxUser.getOpenId());
        String token = JWTUtil.getToken(loginInfo);
        WxMaLogin loginResult = new WxMaLogin();
        loginResult.setToken(token);
        return loginResult;
    }
}
