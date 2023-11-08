package com.safziy.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.safziy.entity.User;
import com.safziy.mapper.UserMapper;
import com.safziy.service.UserService;
import org.springframework.stereotype.Service;

/**
 *  服务层实现。
 *
 * @author safziy
 * @since 2023-11-08
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
