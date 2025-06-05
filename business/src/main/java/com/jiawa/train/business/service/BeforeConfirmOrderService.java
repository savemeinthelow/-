package com.jiawa.train.business.service;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import com.jiawa.train.business.enums.RedisKeyPreEnum;
import com.jiawa.train.business.enums.RocketMQTopicEnum;
import com.jiawa.train.business.mapper.ConfirmOrderMapper;
import com.jiawa.train.business.req.ConfirmOrderDoReq;
import com.jiawa.train.common.context.LoginMemberContext;
import com.jiawa.train.common.exception.BusinessException;
import com.jiawa.train.common.exception.BusinessExceptionEnum;
import jakarta.annotation.Resource;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class BeforeConfirmOrderService {
    private static final Logger LOG = LoggerFactory.getLogger(ConfirmOrderService.class);

    @Resource
    private ConfirmOrderMapper confirmOrderMapper;

    @Resource
    private DailyTrainTicketService dailyTrainTicketService;
    @Resource
    private DailyTrainSeatService dailyTrainSeatService;
    @Resource
    private DailyTrainCarriageService dailyTrainCarriageService;

    @Resource
    private AfterConfirmOrderService afterConfirmOrderService;

    @Autowired
    private RedissonClient redissonClient;

    @Resource
    private RocketMQTemplate rocketMQTemplate;
    @Resource
    private SkTokenService skTokenService;

    public void beforeDoConfirm(ConfirmOrderDoReq req) {
        boolean validSkToken = skTokenService.validSkToken(req.getDate(), req.getTrainCode(), LoginMemberContext.getId());
        if (validSkToken) {
            LOG.info("令牌校验通过");
        } else {
            LOG.info("令牌校验不通过");
            throw new BusinessException(BusinessExceptionEnum.CONFIRM_ORDER_SK_TOKEN_FAIL);
        }
        //分布式锁防止票超卖
        String lockKey = RedisKeyPreEnum.CONFIRM_ORDER + DateUtil.formatDate(req.getDate()) + "-" + req.getTrainCode();
        RLock lock = null;
        lock = redissonClient.getLock(lockKey);
        boolean tryLock = false;
        try {
            tryLock = lock.tryLock(0, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        if (tryLock) {
            LOG.info("恭喜，抢到锁了！");
        } else {
            LOG.info("很遗憾没抢到锁");
            throw new BusinessException(BusinessExceptionEnum.CONFIRM_ORDER_LOCK_FAIL);
        }
        String reqJson = JSON.toJSONString(req);
        rocketMQTemplate.convertAndSend(RocketMQTopicEnum.CONFIRM_ORDER.getCode(),reqJson);
         LOG.info("发送mq开始，消息：{}", reqJson);
    }
}
