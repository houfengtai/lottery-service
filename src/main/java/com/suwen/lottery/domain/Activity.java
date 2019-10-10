package com.suwen.lottery.domain;

import lombok.Data;

import java.util.Date;

@Data
public class Activity {
    private Integer id;

    private String activityName;

    private Date startTime;

    private Date endTime;

    private Integer status;

    private String remark;

}