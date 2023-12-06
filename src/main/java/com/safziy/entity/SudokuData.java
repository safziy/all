package com.safziy.entity;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *  实体类。
 *
 * @author safziy
 * @since 2023-11-23
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "t_sudoku_data")
public class SudokuData implements Serializable {

    @Id(keyType = KeyType.Auto)
    private Integer id;

    /**
     * 数独size
     */
    private String size;

    /**
     * 难度
     */
    private Integer difficulty;

    /**
     * 关卡
     */
    private Integer level;

    /**
     * 原始数据
     */
    @Column(value = "srcData")
    private String srcData;

    /**
     * 目标数据
     */
    @Column(value = "targetData")
    private String targetData;

}
