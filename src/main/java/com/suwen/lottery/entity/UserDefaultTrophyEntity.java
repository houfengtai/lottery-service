package com.suwen.lottery.entity;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class UserDefaultTrophyEntity {
    private Integer id;

    @NotBlank(message = "手机号码不能为空")
    private String phone;
    @NotNull(message = "奖品Id不能为空")
    private Integer trophyId;
    @NotNull(message = "活动Id不能为空")
    private Integer activityId;
    @NotNull(message = "可中奖次数不能为空")
    private Integer prizeTime;
    @NotNull(message = "第几次才中奖不能为空")
    private Integer howManyTime;

}