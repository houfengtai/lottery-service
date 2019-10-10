package com.suwen.lottery.dao;

import com.suwen.lottery.domain.Activity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ActivityMapper {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(Activity record);

    Activity selectByPrimaryKey(Integer id);

    Activity selectByStatus(@Param(value = "status") Integer status);

    int updateByPrimaryKeySelective(Activity record);
}