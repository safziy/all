package com.safziy.service;

import com.safziy.controller.request.FinishSudokuReq;
import com.safziy.controller.response.UserSudokuResp;

public interface UserSudokuService {

    /**
     * 获取用户数独数据
     *
     * @param userId 用户Id
     * @return 用户数独数据
     */
    UserSudokuResp getUserSudokuData(Integer userId);

    /**
     * 用户完成数独
     *
     * @param userId 用户Id
     * @param req    完成数据
     */
    void finishSudoku(Integer userId, FinishSudokuReq req);
}
