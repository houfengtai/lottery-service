package com.suwen.lottery.controller;

import com.suwen.framework.core.commons.resp.Response;
import com.suwen.lottery.dao.UserDefaultTrophyMapper;
import com.suwen.lottery.domain.UserDefaultTrophy;
import com.suwen.lottery.domain.UserDefaultTrophyView;
import com.suwen.lottery.entity.UserDefaultTrophyEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/api/admin")
@Api("后台-设置中奖人")
public class UserDefaultTrophyController {
    @Autowired
    private UserDefaultTrophyMapper userDefaultTrophyMapper;

    @GetMapping("/setting/users")
    @ApiOperation(value = "查询指定中奖人列表")
    public Response<String> findUsers () {
        List<UserDefaultTrophyView> list = userDefaultTrophyMapper.selectAll();
        return Response.returnData(list);
    }

    @PostMapping("/setting/user")
    @ApiOperation(value = "设置指定中奖人信息")
    @ApiImplicitParam(name = "userDefaultTrophyEntity", value = "phone:手机号码;trophyId:奖品id;activityId:活动id;prizeTime:可中奖次数;howManyTime:第几次才中奖;", paramType = "body", dataType = "UserDefaultTrophyEntity")
    public Response<String> settingUser (@RequestBody @Valid UserDefaultTrophyEntity userDefaultTrophyEntity) {
        UserDefaultTrophy userDefaultTrophy = new UserDefaultTrophy();
        BeanUtils.copyProperties(userDefaultTrophyEntity,userDefaultTrophy);
        userDefaultTrophyMapper.insertSelective(userDefaultTrophy);
        return Response.returnData("保存成功!");
    }
    @PutMapping("/setting/user")
    @ApiOperation(value = "编辑指定中奖人信息")
    @ApiImplicitParam(name = "userDefaultTrophyEntity", value = "id;phone:手机号码;trophyId:奖品id;activityId:活动id;prizeTime:可中奖次数;howManyTime:第几次才中奖;", paramType = "body", dataType = "UserDefaultTrophyEntity")
    public Response<String> put(@RequestBody @Valid UserDefaultTrophyEntity userDefaultTrophyEntity) {
        UserDefaultTrophy userDefaultTrophy = new UserDefaultTrophy();
        BeanUtils.copyProperties(userDefaultTrophyEntity,userDefaultTrophy);
        userDefaultTrophyMapper.updateByPrimaryKeySelective(userDefaultTrophy);
        return Response.returnData("修改成功!");
    }

    @DeleteMapping("/setting/user/{id}")
    @ApiOperation(value = "删除指定中奖人信息")
    @ApiImplicitParam(name = "id", value = "", paramType = "path", dataType = "Integer")
    public Response<String> deleteUserPrize (@PathVariable Integer id) {
        userDefaultTrophyMapper.deleteByPrimaryKey(id);
        return Response.returnData("删除成功!");
    }
}
