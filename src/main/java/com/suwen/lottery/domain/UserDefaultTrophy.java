package com.suwen.lottery.domain;

import lombok.Data;

@Data
public class UserDefaultTrophy {
    private Integer id;

    private String phone;

    private Integer trophyId;

    private Integer activityId;

    private Integer prizeTime;

    private Integer howManyTime;

}