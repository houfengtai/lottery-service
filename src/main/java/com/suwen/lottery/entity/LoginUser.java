package com.suwen.lottery.entity;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LoginUser {
    @NotBlank(message = "登录账号不能为空")
    private String userName;
    @NotBlank(message = "登录密码不能为空")
    private String password;
}
