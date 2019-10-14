package com.suwen.lottery.controller;

import com.suwen.framework.core.commons.resp.Response;
import com.suwen.lottery.dao.UserDefaultTrophyMapper;
import com.suwen.lottery.domain.UserDefaultTrophy;
import com.suwen.lottery.entity.UserDefaultTrophyEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/admin")
@Api("后台-设置中奖人")
public class UserDefaultTrophyController {
    @Autowired
    private UserDefaultTrophyMapper userDefaultTrophyMapper;

    @PostMapping("/setting/user")
    @ApiOperation(value = "设置指定中奖人信息")
    @ApiImplicitParam(name = "userDefaultTrophyEntity", value = "phone:手机号码;trophyId:奖品id;activityId:活动id;prizeTime:可中奖次数;howManyTime:第几次才中奖;", paramType = "body", dataType = "UserDefaultTrophyEntity")
    public Response<String> settingUser (@RequestBody @Valid UserDefaultTrophyEntity userDefaultTrophyEntity) {
        UserDefaultTrophy userDefaultTrophy = new UserDefaultTrophy();
        BeanUtils.copyProperties(userDefaultTrophyEntity,userDefaultTrophy);
        userDefaultTrophyMapper.insertSelective(userDefaultTrophy);
        return Response.returnData("保存成功!");
    }
}
