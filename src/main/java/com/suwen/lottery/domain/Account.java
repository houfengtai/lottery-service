package com.suwen.lottery.domain;

import lombok.Data;

import java.util.Date;

@Data
public class Account {
    private Integer id;

    private String loginName;

    private String password;

    private String anotherName;

    private String name;

    private Integer type;

    private Date createTime;

    private Integer status;

}