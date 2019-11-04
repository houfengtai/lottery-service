package com.suwen.lottery.controller;


import com.suwen.framework.core.commons.exception.CustomException;
import com.suwen.framework.core.commons.resp.Response;
import com.suwen.framework.core.commons.utils.ObjectUtil;
import com.suwen.lottery.dao.LotteryLogMapper;
import com.suwen.lottery.domain.Activity;
import com.suwen.lottery.domain.LotteryLog;
import com.suwen.lottery.entity.ActivityEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/admin")
@Api("后台-中奖记录管理")
public class UserTrophyLogController {
    @Autowired
    private LotteryLogMapper lotteryLogMapper;

    @GetMapping("/user/trophes")
    @ApiOperation(value = "查询中奖记录")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "phone", value = "手机号码", paramType = "query", dataType = "String"),
        @ApiImplicitParam(name = "activityId", value = "活动Id", paramType = "query", dataType = "String")
    })
    public Response<String> add (@RequestParam(value = "phone", required = false) String phone,
                                 @RequestParam(value = "activityId", required = false) Integer activityId) throws CustomException {
        String p = ObjectUtil.isNull(phone)?null:"%"+phone+"%";
        List<LotteryLog> list = lotteryLogMapper.selectAll(p,activityId);
        return Response.returnData(list);
    }

}
