package com.safziy.entity;

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
 * @since 2023-11-13
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "t_user_sudoku")
public class UserSudoku implements Serializable {

    @Id(keyType = KeyType.Auto)
    private Integer id;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 难度
     */
    private Integer difficulty;

    /**
     * 关卡
     */
    private Integer level;

    /**
     * 创建时间
     */
    private Timestamp createdAt;

    /**
     * 更新时间
     */
    private Timestamp updatedAt;

}
