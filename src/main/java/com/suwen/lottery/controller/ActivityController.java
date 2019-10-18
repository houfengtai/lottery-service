package com.suwen.lottery.controller;

import com.suwen.framework.core.commons.annotation.IgnoreSecurity;
import com.suwen.framework.core.commons.exception.CustomException;
import com.suwen.framework.core.commons.resp.Response;
import com.suwen.framework.core.commons.rest.BaseController;
import com.suwen.lottery.dao.ActivityMapper;
import com.suwen.lottery.domain.Activity;
import com.suwen.lottery.entity.ActivityEntity;
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
@Api("后台-活动管理")
public class ActivityController extends BaseController {
    @Autowired
    private ActivityMapper activityMapper;

    @PostMapping("/activity")
    @ApiOperation(value = "新增活动")
    @ApiImplicitParam(name = "activityEntity", value = "activityName:活动名称;startTime:活动开始时间;endTime:活动结束时间;remark:活动简介，活动主语事项;", paramType = "body", dataType = "ActivityEntity")
    public Response<String> add (@RequestBody @Valid ActivityEntity activityEntity) throws CustomException{
        Activity activity = new Activity();
        BeanUtils.copyProperties(activityEntity, activity);
        activityMapper.insertSelective(activity);
        return Response.returnData("保存成功!");
    }
    @PutMapping("/activity/stop/{id}")
    @ApiOperation(value = "暂停活动")
    @ApiImplicitParam(name = "id", value = "id:活动id;", paramType = "path", dataType = "int")
    public Response<String> stop (@PathVariable Integer id) throws CustomException{
        Activity activity = new Activity();
        activity.setId(id);
        activity.setStatus(0);
        activityMapper.updateByPrimaryKeySelective(activity);
        return Response.returnData("暂停成功!");
    }
    @PutMapping("/activity/start/{id}")
    @ApiOperation(value = "启动活动")
    @ApiImplicitParam(name = "id", value = "id:活动id;", paramType = "path", dataType = "int")
    public Response<String> start (@PathVariable Integer id) throws CustomException {
        //启动前先暂停所有活动
        activityMapper.updateAll2Stop();

        Activity activity = new Activity();
        activity.setId(id);
        activity.setStatus(1);
        activityMapper.updateByPrimaryKeySelective(activity);
        return Response.returnData("启动成功!");
    }

    @GetMapping("/activites")
    @ApiOperation(value = "查找活动列表")
    public Response<String> find () throws CustomException {
        List<Activity> list = activityMapper.selectAll();
        return Response.returnData(list);
    }

}
