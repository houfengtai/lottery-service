package com.suwen.lottery.entity;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class UserEntity {
    @NotNull(message = "id不能为空")
    private Integer id;
    private Integer seatNo;
    @NotBlank(message = "手机号码不能为空")
    private String phone;
    @NotNull(message = "可抽奖次数不能为空")
    private Integer time;

    private Date createTime;
}
