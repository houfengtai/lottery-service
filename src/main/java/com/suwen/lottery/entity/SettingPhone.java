package com.suwen.lottery.entity;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class SettingPhone {
    @NotBlank(message = "手机号码不能为空")
    private String phone;
    @NotBlank(message = "抽奖次数不能为空")
    private Integer num;
    private Integer seatNo;
}
