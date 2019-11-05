package com.suwen.lottery.dao;

import com.suwen.lottery.domain.UserDefaultTrophy;
import com.suwen.lottery.domain.UserDefaultTrophyView;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserDefaultTrophyMapper {
    int deleteByPrimaryKey(Integer id);

    int deleteByTrophyId(@Param(value = "trophyId") Integer trophyId);

    int insertSelective(UserDefaultTrophy record);

    UserDefaultTrophy selectByPrimaryKey(Integer id);

    UserDefaultTrophy selectByPhoneAndActivityId(@Param(value = "phone") String phone, @Param(value = "activityId") Integer activityId);

    int updateByPrimaryKeySelective(UserDefaultTrophy record);

    int cleanUserDefaultTrophy();

    List<UserDefaultTrophyView> selectAll();
}