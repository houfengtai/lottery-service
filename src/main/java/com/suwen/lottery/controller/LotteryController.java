package com.suwen.lottery.controller;

import com.suwen.framework.core.commons.annotation.IgnoreSecurity;
import com.suwen.lottery.entity.SettingPhone;
import com.suwen.lottery.entity.VerifyPhone;
import com.suwen.lottery.service.LotteryService;
import com.suwen.framework.core.commons.exception.CustomException;
import com.suwen.framework.core.commons.resp.Response;
import com.suwen.framework.core.commons.rest.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by hou on 2019/9/29.
 */
@RestController
@RequestMapping(value = "/api")
@Api("抽奖")
public class LotteryController extends BaseController {
    @Autowired
    private LotteryService lotteryService;

    @GetMapping("/lottery")
    @ApiOperation(value = "抽奖")
    @ApiImplicitParam(name = "tokenId", value = "token值", paramType = "query", dataType = "string")
    public Response<String> lottery() throws CustomException {
        return lotteryService.lottery(Integer.parseInt(getUserId()));
    }

    @PostMapping("/verify")
    @ApiOperation(value = "绑定手机")
    @ApiImplicitParam(name = "verifyPhone", value = "phone:手机号码;randomId:页面随机数;", paramType = "body", dataType = "VerifyPhone")
    @IgnoreSecurity
    public Response<String> settingPhone(@RequestBody @Valid VerifyPhone verifyPhone) throws CustomException {
        return lotteryService.verifyPhone(verifyPhone);
    }

    @GetMapping("/trophy")
    @ApiOperation(value = "查询中奖记录")
    @ApiImplicitParam(name = "tokenId", value = "token值", paramType = "query", dataType = "string")
    public Response<String> findTrophy() throws CustomException {
        return lotteryService.findTrophy(Integer.parseInt(getUserId()));
    }
    @GetMapping("/time")
    @ApiOperation(value = "查询可抽奖次数")
    @ApiImplicitParam(name = "tokenId", value = "token值", paramType = "query", dataType = "string")
    public Response<String> findLotteryTime() throws CustomException {
        return lotteryService.findLotteryTime(Integer.parseInt(getUserId()));
    }

    @GetMapping("/init")
    @ApiOperation(value = "初始化奖品信息")
    @IgnoreSecurity
    public Response<String> initTrophyInfo() throws CustomException {
        return lotteryService.initTrophyInfo();
    }

}
