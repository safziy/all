package com.safziy.controller;

import com.safziy.controller.base.Resp;
import com.safziy.controller.base.WebSupport;
import com.safziy.controller.request.FinishSudokuReq;
import com.safziy.controller.request.SudokuDataReq;
import com.safziy.controller.request.UserSudokuDataReq;
import com.safziy.controller.response.SudokuDataResp;
import com.safziy.controller.response.UserSudokuResp;
import com.safziy.service.SudokuService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sudoku")
public class SudokuController implements WebSupport {

    @Resource
    SudokuService sudokuService;

    /**
     * 获取数独关卡数据
     */
    @GetMapping("sudokuData")
    public Resp<SudokuDataResp> getSudokuData(SudokuDataReq req) {
        SudokuDataResp sudokuData = sudokuService.getSudokuData(req);
        return success(sudokuData);
    }

    /**
     * 获取用户数独关卡进度数据
     */
    @GetMapping("userSudokuData")
    public Resp<UserSudokuResp> getUserSudokuData(UserSudokuDataReq req) {
        UserSudokuResp userData = sudokuService.getUserSudokuData(getLoginUser(), req);
        return success(userData);
    }

    /**
     * 用户完成数独
     */
    @PostMapping("finishSudoku")
    public Resp<Object> finishSudoku(@RequestBody FinishSudokuReq req) {
        sudokuService.finishSudoku(getLoginUser(), req);
        return success(null);
    }

}
