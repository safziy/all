package com.safziy.service;

import com.safziy.controller.request.FinishSudokuReq;
import com.safziy.controller.request.SudokuDataReq;
import com.safziy.controller.request.UserSudokuDataReq;
import com.safziy.controller.response.SudokuDataResp;
import com.safziy.controller.response.UserSudokuResp;

public interface SudokuService {

    /**
     * 获取数据关卡数据
     * @return 数据关卡数据
     */
    SudokuDataResp getSudokuData(SudokuDataReq req);

    /**
     * 获取用户数独数据
     *
     * @param userId 用户Id
     * @return 用户数独数据
     */
    UserSudokuResp getUserSudokuData(Integer userId, UserSudokuDataReq req);

    /**
     * 用户完成数独
     *
     * @param userId 用户Id
     * @param req    完成数据
     */
    void finishSudoku(Integer userId, FinishSudokuReq req);

}
