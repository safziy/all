package com.safziy.service.impl;

import com.mybatisflex.core.query.QueryWrapper;
import com.safziy.controller.request.FinishSudokuReq;
import com.safziy.controller.response.UserSudokuItem;
import com.safziy.controller.response.UserSudokuResp;
import com.safziy.entity.UserSudoku;
import com.safziy.mapper.UserSudokuMapper;
import com.safziy.service.UserSudokuService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.safziy.entity.table.UserSudokuTableDef.USER_SUDOKU;

@Service
public class UserSudokuServiceImpl implements UserSudokuService {

    @Resource
    UserSudokuMapper userSudokuMapper;

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
    }
}
