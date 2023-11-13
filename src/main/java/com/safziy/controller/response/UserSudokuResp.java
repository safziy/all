package com.safziy.controller.response;

import lombok.Data;

import java.util.List;

@Data
public class UserSudokuResp {
    /**
     * 用户数据
     */
    private List<UserSudokuItem> userSudoku;
}
