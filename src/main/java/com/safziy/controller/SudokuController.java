package com.safziy.controller;

import com.safziy.controller.base.Resp;
import com.safziy.controller.base.WebSupport;
import com.safziy.controller.request.FinishSudokuReq;
import com.safziy.controller.response.UserSudokuResp;
import com.safziy.service.UserSudokuService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sudoku")
public class SudokuController implements WebSupport {

    @Resource
    UserSudokuService userSudokuService;

    @GetMapping("userSudokuData")
    public Resp<UserSudokuResp> getUserSudokuData() {
        UserSudokuResp userData = userSudokuService.getUserSudokuData(getLoginUser());
        return success(userData);
    }

    @PostMapping("finishSudoku")
    public Resp<Object> finishSudoku(@RequestBody FinishSudokuReq req) {
        userSudokuService.finishSudoku(getLoginUser(), req);
        return success(null);
    }

}
