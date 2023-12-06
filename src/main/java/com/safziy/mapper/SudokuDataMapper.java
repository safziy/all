package com.safziy.mapper;

import com.mybatisflex.core.BaseMapper;
import com.safziy.entity.SudokuData;
import org.apache.ibatis.annotations.Mapper;

/**
 *  映射层。
 *
 * @author safziy
 * @since 2023-11-23
 */
@Mapper
public interface SudokuDataMapper extends BaseMapper<SudokuData> {

}
