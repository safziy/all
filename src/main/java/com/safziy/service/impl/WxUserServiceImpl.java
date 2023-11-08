package com.safziy.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.safziy.entity.WxUser;
import com.safziy.mapper.WxUserMapper;
import com.safziy.service.WxUserService;
import org.springframework.stereotype.Service;

/**
 *  服务层实现。
 *
 * @author safziy
 * @since 2023-11-08
 */
@Service
public class WxUserServiceImpl extends ServiceImpl<WxUserMapper, WxUser> implements WxUserService {

}
