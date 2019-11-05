package com.suwen.lottery.domain;
import lombok.Data;

@Data
public class UserDefaultTrophyView {
    private Integer id;

    private String phone;

    private Integer trophyId;
    private String trophyName;

    private Integer activityId;
    private String activityName;

    private Integer prizeTime;

    private Integer howManyTime;
}
