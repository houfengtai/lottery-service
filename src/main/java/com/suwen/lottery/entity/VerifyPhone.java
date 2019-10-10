package com.suwen.lottery.entity;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class VerifyPhone {
    @NotBlank(message = "手机号码不能为空")
    private String phone;
    @NotBlank(message = "页面随机数不能为空")
    private String randomId;
}
