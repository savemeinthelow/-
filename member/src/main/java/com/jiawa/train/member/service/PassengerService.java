package com.jiawa.train.member.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.ObjectUtil;
import com.jiawa.train.common.context.LoginMemberContext;
import com.jiawa.train.common.util.SnowUtil;
import com.jiawa.train.member.domain.Passenger;
import com.jiawa.train.member.domain.PassengerExample;
import com.jiawa.train.member.mapper.PassengerMapper;
import com.jiawa.train.member.req.PassengerQueryReq;
import com.jiawa.train.member.req.PassengerSaveReq;
import com.jiawa.train.member.response.PassengerQueryResp;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PassengerService {
    @Resource
    private PassengerMapper mapper;

    public void save(PassengerSaveReq req) {
        DateTime now = DateTime.now();
        Passenger passenger = BeanUtil.copyProperties(req, Passenger.class);
        passenger.setId(SnowUtil.getSnowFlakeNextId());
        passenger.setMemberId(LoginMemberContext.getMember().getId());
        passenger.setCreateTime(now);
        passenger.setUpdateTime(now);
        mapper.insert(passenger);
    }

    public List<PassengerQueryResp>  queryList(PassengerQueryReq req) {
        PassengerExample passengerExample = new PassengerExample();
        PassengerExample.Criteria criteria = passengerExample.createCriteria();
        if (ObjectUtil.isNotNull(req.getMemberId()))
            criteria.andMemberIdEqualTo(req.getMemberId());
        List<Passenger> passengers = mapper.selectByExample(passengerExample);
        List<PassengerQueryResp> passengerQueryResps = BeanUtil.copyToList(passengers, PassengerQueryResp.class);
        return passengerQueryResps;
    }

}
