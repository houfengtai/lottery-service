package com.suwen.lottery.job;

import com.suwen.lottery.dao.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ClearUserTask {
    @Autowired
    private UserMapper userMapper;

    //每天半夜12点半执行
    @Scheduled(cron = "0 30 0 * * *")
    public void clearUser () {
        userMapper.cleanUser();
    }
}
