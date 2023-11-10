package com.safziy.mapper;

import com.mybatisflex.core.BaseMapper;
import com.safziy.entity.WxUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 映射层。
 *
 * @author safziy
 * @since 2023-11-08
 */
@Mapper
public interface WxUserMapper extends BaseMapper<WxUser> {

    /**
     * 根据微信openId查询微信用户信息
     *
     * @param openId 微信open_id
     * @return 微信用户信息
     */
    WxUser findByOpenId(@Param("openId") String openId);

}
