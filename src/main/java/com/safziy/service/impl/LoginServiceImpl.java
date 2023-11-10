package com.safziy.service.impl;

import com.safziy.constants.WebConstants;
import com.safziy.entity.User;
import com.safziy.entity.WxUser;
import com.safziy.mapper.UserMapper;
import com.safziy.mapper.WxUserMapper;
import com.safziy.service.LoginService;
import com.safziy.utils.WebUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {

    @Resource
    private WxUserMapper wxUserMapper;
    @Resource
    private UserMapper userMapper;

    @Override
    public WxUser wxMaLogin(String openId) {
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
        HttpSession session = WebUtil.getSession();
        session.setAttribute(WebConstants.LoginUserId, wxUser.getUserId());
        return wxUser;
    }
}
