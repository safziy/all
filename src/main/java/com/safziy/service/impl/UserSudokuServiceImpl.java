package com.safziy.service.impl;

import com.mybatisflex.core.query.QueryWrapper;
import com.safziy.controller.request.FinishSudokuReq;
import com.safziy.controller.response.UserSudokuItem;
import com.safziy.controller.response.UserSudokuResp;
import com.safziy.entity.UserSudoku;
import com.safziy.entity.UserSudokuHistory;
import com.safziy.mapper.UserSudokuHistoryMapper;
import com.safziy.mapper.UserSudokuMapper;
import com.safziy.service.UserSudokuService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.safziy.entity.table.UserSudokuHistoryTableDef.USER_SUDOKU_HISTORY;
import static com.safziy.entity.table.UserSudokuTableDef.USER_SUDOKU;

@Service
public class UserSudokuServiceImpl implements UserSudokuService {

    @Resource
    UserSudokuMapper userSudokuMapper;
    @Resource
    UserSudokuHistoryMapper userSudokuHistoryMapper;

    @Override
    public UserSudokuResp getUserSudokuData(Integer userId) {
        QueryWrapper query = QueryWrapper.create().select().where(USER_SUDOKU.USER_ID.eq(userId));
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
                .and(USER_SUDOKU.DIFFICULTY.eq(req.getDifficulty()));
        UserSudoku userSudoku = userSudokuMapper.selectOneByQuery(query);
        if (userSudoku == null) {
            userSudoku = new UserSudoku();
            userSudoku.setUserId(userId);
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
                .and(USER_SUDOKU_HISTORY.DIFFICULTY.eq(req.getDifficulty()))
                .and(USER_SUDOKU_HISTORY.LEVEL.eq(req.getDifficulty()));
        UserSudokuHistory history = userSudokuHistoryMapper.selectOneByQuery(historyQuery);
        if (history == null) {
            history = new UserSudokuHistory();
            history.setUserId(userId);
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
