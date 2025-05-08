package com.jiawa.train.business.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jiawa.train.business.domain.DailyTrainSeat;
import com.jiawa.train.business.domain.DailyTrainSeatExample;
import com.jiawa.train.business.domain.TrainSeat;
import com.jiawa.train.business.domain.TrainStation;
import com.jiawa.train.business.mapper.DailyTrainSeatMapper;
import com.jiawa.train.business.req.DailyTrainSeatQueryReq;
import com.jiawa.train.business.req.DailyTrainSeatSaveReq;
import com.jiawa.train.business.resp.DailyTrainSeatQueryResp;
import com.jiawa.train.common.resp.PageResp;
import com.jiawa.train.common.util.SnowUtil;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class DailyTrainSeatService {

    private static final Logger LOG = LoggerFactory.getLogger(DailyTrainSeatService.class);

    @Resource
    private DailyTrainSeatMapper dailyTrainSeatMapper;

    @Resource
    private TrainStationService trainStationService;

    @Resource
    private TrainSeatService trainSeatService;

    public void save(DailyTrainSeatSaveReq req) {
        DateTime now = DateTime.now();
        DailyTrainSeat dailyTrainSeat = BeanUtil.copyProperties(req, DailyTrainSeat.class);
        if (ObjectUtil.isNull(dailyTrainSeat.getId())) {
            dailyTrainSeat.setId(SnowUtil.getSnowflakeNextId());
            dailyTrainSeat.setCreateTime(now);
            dailyTrainSeat.setUpdateTime(now);
            dailyTrainSeatMapper.insert(dailyTrainSeat);
        } else {
            dailyTrainSeat.setUpdateTime(now);
            dailyTrainSeatMapper.updateByPrimaryKey(dailyTrainSeat);
        }
    }

    public PageResp<DailyTrainSeatQueryResp> queryList(DailyTrainSeatQueryReq req) {
        DailyTrainSeatExample dailyTrainSeatExample = new DailyTrainSeatExample();
        dailyTrainSeatExample.setOrderByClause("date desc,train_code asc,carriage_index asc,carriage_seat_index asc");
        DailyTrainSeatExample.Criteria criteria = dailyTrainSeatExample.createCriteria();
        if (StrUtil.isNotBlank(req.getTrainCode())) {
            System.out.println(req.getTrainCode());
            criteria.andTrainCodeEqualTo(req.getTrainCode());
        }
        LOG.info("查询页码：{}", req.getPage());
        LOG.info("每页条数：{}", req.getSize());
        PageHelper.startPage(req.getPage(), req.getSize());
        List<DailyTrainSeat> dailyTrainSeatList = dailyTrainSeatMapper.selectByExample(dailyTrainSeatExample);

        PageInfo<DailyTrainSeat> pageInfo = new PageInfo<>(dailyTrainSeatList);
        LOG.info("总行数：{}", pageInfo.getTotal());
        LOG.info("总页数：{}", pageInfo.getPages());

        List<DailyTrainSeatQueryResp> list = BeanUtil.copyToList(dailyTrainSeatList, DailyTrainSeatQueryResp.class);

        PageResp<DailyTrainSeatQueryResp> pageResp = new PageResp<>();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(list);
        return pageResp;
    }

    public void delete(Long id) {
        dailyTrainSeatMapper.deleteByPrimaryKey(id);
    }
    public void genDaily( String trainCode,Date date) {
        DailyTrainSeatExample dailyTrainSeatExample = new DailyTrainSeatExample();
        DailyTrainSeatExample.Criteria criteria = dailyTrainSeatExample.createCriteria();
        criteria.andTrainCodeEqualTo(trainCode).andDateEqualTo(date);
        dailyTrainSeatMapper.deleteByExample(dailyTrainSeatExample);

        List<TrainSeat> trainSeats = trainSeatService.selectByTrainCode(trainCode);
        if (CollUtil.isEmpty(trainSeats)){
            LOG.info("该列车无座位数据！任务结束");
            return;
        }
        Date now = new Date();
        List<TrainStation> trainStations = trainStationService.selectByTrainCode(trainCode);
        String sell = StrUtil.fillBefore("",'0',trainStations.size()-1);
        for (TrainSeat trainSeat : trainSeats) {
            DailyTrainSeat dailyTrainSeat = BeanUtil.copyProperties(trainSeat, DailyTrainSeat.class);
            dailyTrainSeat.setUpdateTime(now);
            dailyTrainSeat.setSell(sell);
            dailyTrainSeat.setCreateTime(now);
            dailyTrainSeat.setDate(date);
            dailyTrainSeat.setId(SnowUtil.getSnowflakeNextId());
            dailyTrainSeatMapper.insert(dailyTrainSeat);
        }
    }
    public int countSeat(Date date,String trainCode,String seatType){
        DailyTrainSeatExample example = new DailyTrainSeatExample();
        DailyTrainSeatExample.Criteria criteria = example.createCriteria();
        criteria.andTrainCodeEqualTo(trainCode).andDateEqualTo(date).andSeatTypeEqualTo(seatType);
        long l = dailyTrainSeatMapper.countByExample(example);
        if (l==0L){
            return -1;
        }
        else return Math.toIntExact(l);
    }
}
