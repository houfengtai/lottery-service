package com.suwen.lottery.entity;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UserDefaultTrophyEntity {
    private Integer id;

    @NotBlank(message = "手机号码不能为空")
    private String phone;
    @NotBlank(message = "奖品Id不能为空")
    private Integer trophyId;
    @NotBlank(message = "活动Id不能为空")
    private Integer activityId;
    @NotBlank(message = "可中奖次数不能为空")
    private Integer prizeTime;
    @NotBlank(message = "第几次才中奖不能为空")
    private Integer howManyTime;

}