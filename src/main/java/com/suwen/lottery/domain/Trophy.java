package com.suwen.lottery.domain;

import lombok.Data;

@Data
public class Trophy {
    private Integer id;

    private Integer activityId;

    private String trophyName;

    private Integer amount;

    private Integer probability;

    private String bgColor;

    private String imgUrl;

    private Integer seqNo;

}