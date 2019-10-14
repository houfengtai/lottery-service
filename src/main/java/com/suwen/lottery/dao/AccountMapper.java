package com.suwen.lottery.dao;

import com.suwen.lottery.domain.Account;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AccountMapper {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(Account record);

    Account selectByPrimaryKey(Integer id);

    Account selectByLoginName(@Param(value = "userName") String userName);

    int updateByPrimaryKeySelective(Account record);
}