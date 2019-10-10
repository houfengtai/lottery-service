package com.suwen.lottery.dao;

import com.suwen.lottery.domain.UserDefaultTrophy;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserDefaultTrophyMapper {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(UserDefaultTrophy record);

    UserDefaultTrophy selectByPrimaryKey(Integer id);

    UserDefaultTrophy selectByPhoneAndActivityId(@Param(value = "phone") String phone, @Param(value = "activityId") Integer activityId);

    int updateByPrimaryKeySelective(UserDefaultTrophy record);

}