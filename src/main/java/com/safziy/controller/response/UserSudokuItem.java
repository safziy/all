package com.safziy.controller.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserSudokuItem {
    /**
     * 难度
     */
    private int difficulty;
    /**
     * 关卡
     */
    private int level;
}
