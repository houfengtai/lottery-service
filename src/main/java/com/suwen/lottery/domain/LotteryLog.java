package com.suwen.lottery.domain;

import lombok.Data;

import java.util.Date;

@Data
public class LotteryLog {
    private Integer id;

    private Integer activityId;

    private String phone;

    private String content;

    private Date createTime;

}