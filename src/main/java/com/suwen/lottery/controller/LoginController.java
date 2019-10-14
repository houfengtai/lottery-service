package com.suwen.lottery.controller;

import com.suwen.framework.core.commons.annotation.IgnoreSecurity;
import com.suwen.framework.core.commons.constants.Constants;
import com.suwen.framework.core.commons.exception.CustomException;
import com.suwen.framework.core.commons.resp.Response;
import com.suwen.framework.core.commons.rest.BaseController;
import com.suwen.framework.core.commons.utils.MD5Util;
import com.suwen.framework.core.commons.utils.ObjectUtil;
import com.suwen.framework.core.redis.service.RedisService;
import com.suwen.lottery.dao.AccountMapper;
import com.suwen.lottery.domain.Account;
import com.suwen.lottery.entity.LoginUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/admin")
@Api("后台登录")
public class LoginController extends BaseController {
    @Autowired
    private AccountMapper accountMapper;
    @Autowired
    private RedisService redisService;

    @PostMapping("/login")
    @ApiOperation(value = "后台账号密码登录")
    @ApiImplicitParam(name = "loginUser", value = "userName:账号;password:密码;", paramType = "body", dataType = "LoginUser")
    @IgnoreSecurity
    public Response<String> login (@RequestBody @Valid LoginUser loginUser) throws CustomException {
        Account account = accountMapper.selectByLoginName(loginUser.getUserName());
        if(ObjectUtil.isNull(account)) return new Response(Constants.RESP_STATUS_BADREQUEST, "账号不存在");
        if(!MD5Util.encode(loginUser.getUserName()+loginUser.getPassword()).equals(account.getPassword())) return new Response(Constants.RESP_STATUS_BADREQUEST, "输入的密码不正确");

        String tokenId = UUID.randomUUID().toString().replaceAll("-","");
        //保存token值
        redisService.set("token:" + tokenId,account.getId(), Constants.TOKEN_EXPIRES_HOUR);
        account.setStatus(null);
        account.setType(null);
        account.setPassword(null);
        JSONObject json = new JSONObject();
        json.put("tokenId",tokenId);
        json.put("userInfo",account);
        return Response.returnData(json);
    }
}
