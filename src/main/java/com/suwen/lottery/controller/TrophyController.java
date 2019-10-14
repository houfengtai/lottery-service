package com.suwen.lottery.controller;

import com.suwen.framework.core.commons.constants.Constants;
import com.suwen.framework.core.commons.exception.CustomException;
import com.suwen.framework.core.commons.resp.Response;
import com.suwen.framework.core.commons.utils.ObjectUtil;
import com.suwen.lottery.dao.TrophyMapper;
import com.suwen.lottery.dao.UserDefaultTrophyMapper;
import com.suwen.lottery.domain.Trophy;
import com.suwen.lottery.entity.TrophyEntity;
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
@Api("后台-奖品管理")
public class TrophyController {
    @Autowired
    private TrophyMapper trophyMapper;
    @Autowired
    private UserDefaultTrophyMapper userDefaultTrophyMapper;

    @GetMapping("/trophes")
    @ApiOperation(value = "查询所有奖品项")
    public Response<String> find () throws CustomException{
        List<Trophy> list = trophyMapper.selectAll();
        return Response.returnData(list);
    }

    @PostMapping("/trophy")
    @ApiOperation(value = "新增奖品项")
    @ApiImplicitParam(name = "trophyEntity", value = "activityId:活动id;trophyName:奖品名称;amount:数量(可为空);probability:概率;bgColor:背景颜色;imgUrl:奖品图标(可为空);seqNo:编号;", paramType = "body", dataType = "TrophyEntity")
    public Response<String> add (@RequestBody @Valid TrophyEntity trophyEntity) throws CustomException{
        Trophy trophy = new Trophy();
        BeanUtils.copyProperties(trophyEntity,trophy);
        trophyMapper.insertSelective(trophy);
        return Response.returnData("保存成功!");
    }
    @PutMapping("/trophy")
    @ApiOperation(value = "修改奖品项")
    @ApiImplicitParam(name = "trophyEntity", value = "id:奖品id;activityId:活动id;trophyName:奖品名称;amount:数量(可为空);probability:概率;bgColor:背景颜色;imgUrl:奖品图标(可为空);seqNo:编号;", paramType = "body", dataType = "TrophyEntity")
    public Response<String> update (@RequestBody @Valid TrophyEntity trophyEntity) throws CustomException {
        if(ObjectUtil.isNull(trophyEntity.getId())) return new Response(Constants.RESP_STATUS_BADREQUEST,"id不能为空");
        Trophy trophy = new Trophy();
        BeanUtils.copyProperties(trophyEntity,trophy);
        trophyMapper.updateByPrimaryKeySelective(trophy);
        return Response.returnData("修改成功!");
    }
    @DeleteMapping("/trophy/{id}")
    @ApiOperation(value = "删除奖品项")
    @ApiImplicitParam(name = "id", value = "id:奖品id;", paramType = "path", dataType = "int")
    public Response<String> delete (@PathVariable Integer id) throws CustomException{
        trophyMapper.deleteByPrimaryKey(id);
        userDefaultTrophyMapper.deleteByTrophyId(id);
        return Response.returnData("删除成功!");
    }
}
