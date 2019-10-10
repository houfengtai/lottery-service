package com.suwen.lottery.service;

import com.suwen.framework.core.redis.service.RedisService;
import com.suwen.lottery.dao.*;
import com.suwen.lottery.domain.*;
import com.suwen.framework.core.commons.constants.Constants;
import com.suwen.framework.core.commons.exception.CustomException;
import com.suwen.framework.core.commons.resp.Response;
import com.suwen.framework.core.commons.utils.ObjectUtil;
import com.suwen.lottery.entity.SettingPhone;
import com.suwen.lottery.entity.VerifyPhone;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class LotteryServiceImpl implements LotteryService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ActivityMapper activityMapper;
    @Autowired
    private TrophyMapper trophyMapper;
    @Autowired
    private LotteryLogMapper lotteryLogMapper;
    @Autowired
    private UserDefaultTrophyMapper userDefaultTrophyMapper;
    @Autowired
    private RedisService redisService;

    @Override
    public Response<String> lottery(Integer userId) throws CustomException {
        //获取活动项目
        Activity activity =  activityMapper.selectByStatus(1);
        if(ObjectUtil.isNull(activity)) return new Response(Constants.RESP_STATUS_BADREQUEST, "目前没有进行活动");
        if(ObjectUtil.isNotNull(activity.getStartTime()) && activity.getStartTime().getTime() > new Date().getTime()) return new Response(Constants.RESP_STATUS_BADREQUEST, "活动尚未开始");
        if(ObjectUtil.isNotNull(activity.getEndTime()) && activity.getEndTime().getTime() < new Date().getTime()) return new Response(Constants.RESP_STATUS_BADREQUEST, "活动已结束");

        //获取用户信息
        User user = userMapper.selectByPrimaryKey(userId);
        if(ObjectUtil.isNull(user))  return new Response(Constants.RESP_STATUS_BADREQUEST, "您尚未获得抽奖机会");
        if(user.getTime() == 0)  return new Response(Constants.RESP_STATUS_BADREQUEST, "您的抽奖机会已用完啦");


        //获取奖品列表
        List<Trophy> list = trophyMapper.selectByActivityId(activity.getId());
        if(ObjectUtil.isNull(list)) return new Response(Constants.RESP_STATUS_INTERNAL_ERROR, "系统异常");

        //获取概率数组
        List<Integer> collect = list.stream().map(Trophy::getProbability).collect(Collectors.toList());

        //根据概率获取奖项id（抽奖结果）
        Integer prizeId = getRand(collect);

        //获取已抽奖次数
        int lotteryLogNum = lotteryLogMapper.selectCountByPhoneAndActivityId(user.getPhone(),activity.getId());

        //查询该手机号是否有内定奖品
        UserDefaultTrophy userDefaultTrophy = userDefaultTrophyMapper.selectByPhoneAndActivityId(user.getPhone(),activity.getId());
        if(ObjectUtil.isNotNull(userDefaultTrophy)
                && userDefaultTrophy.getPrizeTime() > 0
                && lotteryLogNum == (ObjectUtil.isNull(userDefaultTrophy.getHowManyTime())?lotteryLogNum:(userDefaultTrophy.getHowManyTime()-1))){
            for (int i = 0; i < list.size(); i++) {
                if(list.get(i).getId() == userDefaultTrophy.getTrophyId()) {
                    prizeId = i;
                    break;
                }
            }
            UserDefaultTrophy udt = new UserDefaultTrophy();
            udt.setId(userDefaultTrophy.getId());
            udt.setPrizeTime(userDefaultTrophy.getPrizeTime() - 1);
            userDefaultTrophyMapper.updateByPrimaryKeySelective(udt);
        }

        //如果有设置奖品数量,判断该奖项数量是否发完
        if(ObjectUtil.isNotNull(list.get(prizeId).getAmount()) && list.get(prizeId).getAmount() == 0){
            //如果已经发完，则设置该概率为0，并从新抽奖
            collect = Collections.singletonList(collect.set(prizeId, 0));
            prizeId = getRand(collect);
            log.info("提示信息：该奖项奖品数量剩余："+list.get(prizeId).getAmount());
        }

        //如果存在设置奖品数量，则修改数量变化
        if(ObjectUtil.isNotNull(list.get(prizeId).getAmount()) && list.get(prizeId).getAmount() != 0){
            Trophy t = new Trophy();
            t.setId(list.get(prizeId).getId());
            t.setAmount(list.get(prizeId).getAmount() - 1);
            trophyMapper.updateByPrimaryKeySelective(t);
        }

        //保存中奖信息
        LotteryLog lotteryLog = new LotteryLog();
        lotteryLog.setActivityId(activity.getId());
        lotteryLog.setContent("谢谢参与".equals(list.get(prizeId).getTrophyName())?list.get(prizeId).getTrophyName():"恭喜获得了"+list.get(prizeId).getTrophyName());
        lotteryLog.setCreateTime(new Date());
        lotteryLog.setPhone(user.getPhone());

        lotteryLogMapper.insertSelective(lotteryLog);

        //用户抽奖次数递减
        User u = new User();
        u.setId(user.getId());
        u.setTime(user.getTime()-1);
        userMapper.updateByPrimaryKeySelective(u);

        log.info("提示信息：恭喜获得了"+list.get(prizeId).getTrophyName());
        //轮盘转动角度，奖项有list.size()份
        double angles = rotateFn(prizeId + 1,list.size());

        JSONObject json = new JSONObject();
        json.put("msg", list.get(prizeId).getTrophyName());
        json.put("angles", angles);
        json.put("level", prizeId + 1);
        if(ObjectUtil.isNotNull(list.get(prizeId).getAmount())) json.put("amount", list.get(prizeId).getAmount());
        return Response.returnData(json);
    }

    @Override
    public Response<String> settingPhone(SettingPhone settingPhone) throws CustomException {
        User user = userMapper.selectByPhone(settingPhone.getPhone());
        if(ObjectUtil.isNull(user)){
            user = new User();
            user.setPhone(settingPhone.getPhone());
            user.setTime(settingPhone.getNum());
            user.setSeatNo(settingPhone.getNum());
            user.setCreateTime(new Date());
            userMapper.insertSelective(user);
        }else{
            user.setTime(settingPhone.getNum());
            user.setSeatNo(settingPhone.getNum());
            userMapper.updateByPrimaryKeySelective(user);
        }
        return Response.returnData("设置成功");
    }

    @Override
    public Response<String> verifyPhone(VerifyPhone verifyPhone) throws CustomException {
        User user = userMapper.selectByPhone(verifyPhone.getPhone());
        if(ObjectUtil.isNull(user)) return new Response(Constants.RESP_STATUS_BADREQUEST,"该号码不符合抽奖规则");
        String tokenId = UUID.randomUUID().toString().replaceAll("-","");
        //保存token值
        redisService.set("token:" + tokenId,user.getId().toString(), Constants.TOKEN_EXPIRES_HOUR);
        JSONObject json = new JSONObject();
        json.put("tokenId",tokenId);
        json.put("time",user.getTime());
        return Response.returnData(json);
    }

    @Override
    public Response<String> findTrophy(Integer userId) throws CustomException {
        User user = userMapper.selectByPrimaryKey(userId);
        Activity activity =  activityMapper.selectByStatus(1);
        List<LotteryLog> list = lotteryLogMapper.selectByPhoneAndActivityId(user.getPhone(),activity.getId());
        return Response.returnData(list);
    }

    @Override
    public Response<String> findLotteryTime(Integer userId) throws CustomException {
        User user = userMapper.selectByPrimaryKey(userId);
        return Response.returnData(user.getTime());
    }

    @Override
    public Response<String> initTrophyInfo() throws CustomException {
        Activity activity =  activityMapper.selectByStatus(1);
        List<Trophy> list = trophyMapper.selectByActivityId(activity.getId());
        JSONObject json = new JSONObject();
        List<String> trophyList = new ArrayList<>();
        List<String> bgColorList = new ArrayList<>();
        List<String> bgImgList = new ArrayList<>();
        list.stream().forEach(x->{
            if(x.getTrophyName() != null) trophyList.add(x.getTrophyName());
            if(x.getBgColor() != null) bgColorList.add(x.getBgColor());
            if(x.getImgUrl() != null) bgImgList.add(x.getImgUrl());
        });
        json.put("trophyList",trophyList);
        json.put("bgColorList",bgColorList);
        json.put("bgImgList",bgImgList);
        json.put("title",activity.getActivityName());
        json.put("startTime",activity.getStartTime());
        json.put("endTime",activity.getEndTime());
        json.put("remark",activity.getRemark());

        return Response.returnData(json);
    }

    //根据概率获取奖项
    private Integer getRand(List<Integer> list){
        Integer result = null;
        try {
            int  sum = list.stream().mapToInt(x->x).sum();//概率数组的总概率精度
            for(int i=0;i<list.size();i++){//概率数组循环
                int randomNum = new Random().nextInt(sum);//随机生成1到sum的整数
                if(randomNum<list.get(i)){//中奖
                    result = i;
                    break;
                }else{
                    sum -=list.get(i);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     *
     * @param randomNum 随机数
     * @param count 总数
     * @return angles 角度
     */
    private double rotateFn(int randomNum,int count){
        double angles =  (randomNum * (360 / count) - (360 / (count*2)));
        if(angles<270){
            angles = 270 - angles;
        }else{
            angles = 360 - angles + 270;
        }
        return angles;
    }

    /**
     *
     * @param n 开始数
     * @param m 结束数
     * @return random 随机数
     */
    private int rnd(int n,int m){
        int random = (int) Math.floor(Math.random()*(m-n+1)+n);
        return random;
    }
}
