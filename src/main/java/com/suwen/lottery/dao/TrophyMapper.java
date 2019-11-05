package com.suwen.lottery.dao;

import com.suwen.lottery.domain.Trophy;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TrophyMapper {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(Trophy record);

    Trophy selectByPrimaryKey(Integer id);

    List<Trophy> selectAll(@Param(value = "activityId") Integer activityId);

    List<Trophy> selectByActivityId(@Param(value = "activityId") Integer activityId);

    int updateByPrimaryKeySelective(Trophy record);
}