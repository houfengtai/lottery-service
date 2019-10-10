package com.suwen.lottery.domain;

import lombok.Data;

import java.util.Date;

@Data
public class User {
    private Integer id;

    private Integer seatNo;

    private String phone;

    private Integer time;

    private Date createTime;

}