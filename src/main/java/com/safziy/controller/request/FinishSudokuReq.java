package com.safziy.controller.request;

import lombok.Data;

@Data
public class FinishSudokuReq {
    /**
     * 难度
     */
    private int difficulty;
    /**
     * 关卡
     */
    private int level;
    /**
     * 用时
     */
    private int costTime;
}
