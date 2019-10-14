package com.suwen.lottery.entity;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
public class ActivityEntity {
    @NotBlank(message = "活动名称不能为空")
    private String activityName;

    private Date startTime;

    private Date endTime;

    private String remark;
}
