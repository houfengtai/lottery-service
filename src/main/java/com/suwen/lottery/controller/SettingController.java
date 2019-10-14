package com.suwen.lottery.controller;

import com.suwen.framework.core.commons.annotation.IgnoreSecurity;
import com.suwen.framework.core.commons.exception.CustomException;
import com.suwen.framework.core.commons.resp.Response;
import com.suwen.framework.core.commons.rest.BaseController;
import com.suwen.lottery.entity.SettingPhone;
import com.suwen.lottery.service.LotteryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/admin")
@Api("后台设置")
public class SettingController extends BaseController {
    @Autowired
    private LotteryService lotteryService;

    @PostMapping("/setting/phone")
    @ApiOperation(value = "设置根据手机号码设置抽奖次数")
    @ApiImplicitParam(name = "settingPhone", value = "phone:手机号码;num:次数;seatNo:座位号;", paramType = "body", dataType = "SettingPhone")
    public Response<String> settingPhone(@RequestBody @Valid SettingPhone settingPhone) throws CustomException {
        return lotteryService.settingPhone(settingPhone);
    }
}
