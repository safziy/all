package com.safziy.entity;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import java.io.Serializable;
import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *  实体类。
 *
 * @author safziy
 * @since 2023-11-08
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "t_wx_user")
public class WxUser implements Serializable {

    @Id(keyType = KeyType.Auto)
    private Integer id;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 微信开发平台union_id
     */
    private String unionId;

    /**
     * 微信open_id
     */
    private String openId;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 性别
     */
    private String gender;

    /**
     * 语言
     */
    private String language;

    /**
     * 城市
     */
    private String city;

    /**
     * 省份
     */
    private String province;

    /**
     * 国家
     */
    private String country;

    /**
     * 头像地址
     */
    @Column(value = "avatarUrl")
    private String avatarUrl;

    /**
     * 创建时间
     */
    private Timestamp createdAt;

    /**
     * 更新时间
     */
    private Timestamp updatedAt;

}
