package com.suwen.lottery.dao;

import com.suwen.lottery.domain.LotteryLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface LotteryLogMapper {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(LotteryLog record);

    LotteryLog selectByPrimaryKey(Integer id);

    List<LotteryLog> selectByPhoneAndActivityId(@Param(value = "phone") String phone,@Param(value = "activityId") Integer activityId);

    List<LotteryLog> selectAll(@Param(value = "phone") String phone,@Param(value = "activityId") Integer activityId);

    int selectCountByPhoneAndActivityId(@Param(value = "phone") String phone,@Param(value = "activityId") Integer activityId);

    int updateByPrimaryKeySelective(LotteryLog record);
}