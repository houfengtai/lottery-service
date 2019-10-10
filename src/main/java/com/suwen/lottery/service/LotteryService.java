package com.suwen.lottery.service;

import com.suwen.framework.core.commons.exception.CustomException;
import com.suwen.framework.core.commons.resp.Response;
import com.suwen.lottery.entity.SettingPhone;
import com.suwen.lottery.entity.VerifyPhone;

public interface LotteryService {
    Response<String> lottery(Integer userId) throws CustomException;
    Response<String> settingPhone(SettingPhone settingPhone) throws CustomException;
    Response<String> verifyPhone(VerifyPhone verifyPhone) throws CustomException;
    Response<String> findTrophy(Integer userId) throws CustomException;
    Response<String> findLotteryTime(Integer userId) throws CustomException;
    Response<String> initTrophyInfo() throws CustomException;
}
