package com.jiawa.train.business.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jiawa.train.business.domain.SkToken;
import com.jiawa.train.business.domain.SkTokenExample;
import com.jiawa.train.business.enums.RedisKeyPreEnum;
import com.jiawa.train.business.mapper.SkTokenMapper;
import com.jiawa.train.business.mapper.cust.SkTokenMapperCust;
import com.jiawa.train.business.req.SkTokenQueryReq;
import com.jiawa.train.business.req.SkTokenSaveReq;
import com.jiawa.train.business.resp.SkTokenQueryResp;
import com.jiawa.train.common.resp.PageResp;
import com.jiawa.train.common.util.SnowUtil;
import jakarta.annotation.Resource;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class SkTokenService {

    private static final Logger LOG = LoggerFactory.getLogger(SkTokenService.class);

    @Resource
    private SkTokenMapper skTokenMapper;

    @Resource
    private DailyTrainSeatService dailyTrainSeatService;

    @Resource
    private DailyTrainStationService dailyTrainStationService;

    @Resource
    private SkTokenMapperCust skTokenMapperCust;

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Value("${spring.profiles.active}")
    private String env;

    public void genDaily(Date date, String trainCode) {
        LOG.info("删除日期【{}】车次【{}】的令牌记录", DateUtil.formatDate(date), trainCode);
        SkTokenExample skTokenExample = new SkTokenExample();
        skTokenExample.createCriteria().andTrainCodeEqualTo(trainCode).andDateEqualTo(date);
        skTokenMapper.deleteByExample(skTokenExample);

        DateTime now = new DateTime();
        SkToken skToken = new SkToken();
        skToken.setId(SnowUtil.getSnowflakeNextId());
        skToken.setDate(date);
        skToken.setTrainCode(trainCode);
        skToken.setCreateTime(now);
        skToken.setUpdateTime(now);

        int seatCount = dailyTrainSeatService.countSeat(date, trainCode);

        long stationCount = dailyTrainStationService.countByTrainCode(trainCode, date);

        int count = (int) (seatCount * stationCount);
        skToken.setCount(count);
        skTokenMapper.insert(skToken);
    }

    public void save(SkTokenSaveReq req) {
        DateTime now = DateTime.now();
        SkToken skToken = BeanUtil.copyProperties(req, SkToken.class);
        if (ObjectUtil.isNull(skToken.getId())) {
            skToken.setId(SnowUtil.getSnowflakeNextId());
            skToken.setCreateTime(now);
            skToken.setUpdateTime(now);
            skTokenMapper.insert(skToken);
        } else {
            skToken.setUpdateTime(now);
            skTokenMapper.updateByPrimaryKey(skToken);
        }
    }

    public PageResp<SkTokenQueryResp> queryList(SkTokenQueryReq req) {
        SkTokenExample skTokenExample = new SkTokenExample();
        skTokenExample.setOrderByClause("id desc");
        SkTokenExample.Criteria criteria = skTokenExample.createCriteria();

        LOG.info("查询页码：{}", req.getPage());
        LOG.info("每页条数：{}", req.getSize());
        PageHelper.startPage(req.getPage(), req.getSize());
        List<SkToken> skTokenList = skTokenMapper.selectByExample(skTokenExample);

        PageInfo<SkToken> pageInfo = new PageInfo<>(skTokenList);
        LOG.info("总行数：{}", pageInfo.getTotal());
        LOG.info("总页数：{}", pageInfo.getPages());

        List<SkTokenQueryResp> list = BeanUtil.copyToList(skTokenList, SkTokenQueryResp.class);

        PageResp<SkTokenQueryResp> pageResp = new PageResp<>();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(list);
        return pageResp;
    }

    public void delete(Long id) {
        skTokenMapper.deleteByPrimaryKey(id);
    }

    public boolean validSkToken(Date date, String trainCode, Long memberId) {
     /*   String lockKey = RedisKeyPreEnum.SK_TOKEN + DateUtil.formatDate(date) + "-" + trainCode + "-" + memberId;
        RLock lock = redissonClient.getLock(lockKey);
        boolean tryLock = false;
        try {
            tryLock = lock.tryLock(0,10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        if (tryLock) {
            LOG.info("恭喜，抢到锁了！");
        } else {
            LOG.info("很遗憾没抢到锁");
            return false;
        }*/
        LOG.info("会员【{}】获取日" +
                "期【{}】车次【{}】的令牌", memberId, date, trainCode);
        String skTokenCountKey = RedisKeyPreEnum.SK_TOKEN_COUNT + "-" + DateUtil.formatDate(date) + "-" + trainCode;
        Object skTokenCount = redisTemplate.opsForValue().get(skTokenCountKey);
        if (skTokenCount != null) {
            LOG.info("缓存中有该车次令牌key:{}", skTokenCountKey);
            Long count = redisTemplate.opsForValue().decrement(skTokenCountKey, 1);
            if (count < 0L) {
                LOG.info("该车次令牌不足:{}", skTokenCountKey);
                return false;
            } else {
                redisTemplate.expire(skTokenCountKey, 60, TimeUnit.SECONDS);
                if (count % 5 == 0) {
                    skTokenMapperCust.decrease(date, trainCode, 5);
                }
                return true;
            }
        } else {
            SkTokenExample skTokenExample = new SkTokenExample();
            skTokenExample.createCriteria().andDateEqualTo(date).andTrainCodeEqualTo(trainCode);
            List<SkToken> skTokens = skTokenMapper.selectByExample(skTokenExample);
            if (CollUtil.isEmpty(skTokens)) {
                LOG.info("找不到日期【{}】车次【{}】的令牌记录", DateUtil.formatDate(date), trainCode);
                return false;
            } else {
                SkToken skToken = skTokens.get(0);
                if (skToken.getCount() <= 0) {
                    LOG.info("该车次令牌不足:{}", skTokenCountKey);
                    return false;
                }
                Integer count = skToken.getCount() - 1;
                redisTemplate.opsForValue().set(skTokenCountKey, String.valueOf(count),60, TimeUnit.SECONDS);
                LOG.info("将该车次令牌大闸放入缓存中，key: {}， count: {}", skTokenCountKey, count);
                return true;
            }
        }
//        int updateCount = skTokenMapperCust.decrease(date, trainCode,5);
//        if (updateCount > 0) {
//            return true;
//        }
//        return false;
//    }
    }
}
