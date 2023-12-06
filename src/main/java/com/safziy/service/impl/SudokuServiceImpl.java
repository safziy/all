package com.safziy.service.impl;

import com.mybatisflex.core.query.QueryWrapper;
import com.safziy.controller.request.FinishSudokuReq;
import com.safziy.controller.request.SudokuDataReq;
import com.safziy.controller.request.UserSudokuDataReq;
import com.safziy.controller.response.SudokuDataResp;
import com.safziy.controller.response.UserSudokuItem;
import com.safziy.controller.response.UserSudokuResp;
import com.safziy.entity.SudokuData;
import com.safziy.entity.UserSudoku;
import com.safziy.entity.UserSudokuHistory;
import com.safziy.mapper.SudokuDataMapper;
import com.safziy.mapper.UserSudokuHistoryMapper;
import com.safziy.mapper.UserSudokuMapper;
import com.safziy.service.SudokuService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.safziy.entity.table.SudokuDataTableDef.SUDOKU_DATA;
import static com.safziy.entity.table.UserSudokuHistoryTableDef.USER_SUDOKU_HISTORY;
import static com.safziy.entity.table.UserSudokuTableDef.USER_SUDOKU;

@Service
public class SudokuServiceImpl implements SudokuService {

    @Resource
    SudokuDataMapper sudokuDataMapper;
    @Resource
    UserSudokuMapper userSudokuMapper;
    @Resource
    UserSudokuHistoryMapper userSudokuHistoryMapper;

    @Override
    public SudokuDataResp getSudokuData(SudokuDataReq req) {
        QueryWrapper query = QueryWrapper.create().select()
                .where(SUDOKU_DATA.SIZE.eq(req.getSize()))
                .and(SUDOKU_DATA.DIFFICULTY.eq(req.getDifficulty()))
                .and(SUDOKU_DATA.LEVEL.eq(req.getLevel()));
        SudokuData sudokuData = sudokuDataMapper.selectOneByQuery(query);
        SudokuDataResp resp = new SudokuDataResp();
        resp.setSize(sudokuData.getSize());
        resp.setDifficulty(sudokuData.getDifficulty());
        resp.setLevel(sudokuData.getLevel());
        resp.setSrcData(sudokuData.getSrcData());
        resp.setTargetData(sudokuData.getTargetData());
        return resp;
    }

    @Override
    public UserSudokuResp getUserSudokuData(Integer userId, UserSudokuDataReq req) {
        QueryWrapper query = QueryWrapper.create().select()
                .where(USER_SUDOKU.USER_ID.eq(userId))
                .and(USER_SUDOKU.SIZE.eq(req.getSize()));
        List<UserSudoku> userData = userSudokuMapper.selectListByQuery(query);

        UserSudokuResp result = new UserSudokuResp();
        result.setUserSudoku(
                userData.stream()
                        .map(userSudoku -> new UserSudokuItem(userSudoku.getDifficulty(), userSudoku.getLevel()))
                        .toList()
        );
        return result;
    }

    @Override
    public void finishSudoku(Integer userId, FinishSudokuReq req) {
        // 保存关卡进度
        QueryWrapper query = QueryWrapper.create().select()
                .where(USER_SUDOKU.USER_ID.eq(userId))
                .and(USER_SUDOKU.SIZE.eq(req.getSize()))
                .and(USER_SUDOKU.DIFFICULTY.eq(req.getDifficulty()));
        UserSudoku userSudoku = userSudokuMapper.selectOneByQuery(query);
        if (userSudoku == null) {
            userSudoku = new UserSudoku();
            userSudoku.setUserId(userId);
            userSudoku.setSize(req.getSize());
            userSudoku.setDifficulty(req.getDifficulty());
            userSudoku.setLevel(req.getLevel());
        } else {
            if (userSudoku.getLevel() < req.getLevel()) {
                userSudoku.setLevel(req.getLevel());
            }
        }
        userSudokuMapper.insertOrUpdate(userSudoku);

        // 保存关卡历史
        QueryWrapper historyQuery = QueryWrapper.create().select()
                .where(USER_SUDOKU_HISTORY.USER_ID.eq(userId))
                .and(USER_SUDOKU_HISTORY.SIZE.eq(req.getSize()))
                .and(USER_SUDOKU_HISTORY.DIFFICULTY.eq(req.getDifficulty()))
                .and(USER_SUDOKU_HISTORY.LEVEL.eq(req.getLevel()));
        UserSudokuHistory history = userSudokuHistoryMapper.selectOneByQuery(historyQuery);
        if (history == null) {
            history = new UserSudokuHistory();
            history.setUserId(userId);
            history.setSize(req.getSize());
            history.setDifficulty(req.getDifficulty());
            history.setLevel(req.getLevel());
            history.setBestCostTime(req.getCostTime());
        } else {
            if (history.getBestCostTime() > req.getCostTime()) {
                history.setBestCostTime(req.getCostTime());
            }
        }
        userSudokuHistoryMapper.insertOrUpdate(history);
    }
}
