package com.suwen.lottery.entity;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class TrophyEntity {
    private Integer id;
    @NotNull(message = "活动id不能为空")
    private Integer activityId;
    @NotBlank(message = "奖品名称不能为空")
    private String trophyName;

    private Integer amount;
    @NotNull(message = "概率不能为空")
    private Integer probability;
    @NotBlank(message = "背景颜色不能为空")
    private String bgColor;

    private String imgUrl;
    @NotNull(message = "编号不能为空")
    private Integer seqNo;

}