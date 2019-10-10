package com.suwen.lottery.dao;

import com.suwen.lottery.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    User selectByPhone(@Param(value = "phone") String phone);

    int updateByPrimaryKeySelective(User record);

    int cleanUser();

}