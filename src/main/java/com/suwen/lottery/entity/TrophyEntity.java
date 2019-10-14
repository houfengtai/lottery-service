package com.suwen.lottery.entity;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class TrophyEntity {
    private Integer id;
    @NotBlank(message = "活动id不能为空")
    private Integer activityId;
    @NotBlank(message = "奖品名称不能为空")
    private String trophyName;

    private Integer amount;
    @NotBlank(message = "概率不能为空")
    private Integer probability;
    @NotBlank(message = "背景颜色不能为空")
    private String bgColor;

    private String imgUrl;
    @NotBlank(message = "编号不能为空")
    private Integer seqNo;

}