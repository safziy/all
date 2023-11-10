package com.safziy.mapper;

import com.mybatisflex.core.BaseMapper;
import com.safziy.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 *  映射层。
 *
 * @author safziy
 * @since 2023-11-08
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
