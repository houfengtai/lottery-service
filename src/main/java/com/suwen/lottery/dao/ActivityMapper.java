package com.suwen.lottery.dao;

import com.suwen.lottery.domain.Activity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ActivityMapper {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(Activity record);

    Activity selectByPrimaryKey(Integer id);

    Activity selectByStatus(@Param(value = "status") Integer status);

    List<Activity> selectAll();

    int updateAll2Stop();

    int updateByPrimaryKeySelective(Activity record);
}