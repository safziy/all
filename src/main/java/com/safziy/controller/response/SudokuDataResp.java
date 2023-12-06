package com.safziy.controller.response;

import lombok.Data;

@Data
public class SudokuDataResp {

    /**
     * 数独规格
     */
    private String size;
    /**
     * 难度
     */
    private int difficulty;
    /**
     * 关卡
     */
    private int level;
    /**
     * 数独原始数据
     */
    private String srcData;
    /**
     * 数独目前数据
     */
    private String targetData;
}
