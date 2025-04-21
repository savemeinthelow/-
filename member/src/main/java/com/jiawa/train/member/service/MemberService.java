package com.jiawa.train.member.service;

import cn.hutool.core.collection.CollUtil;
import com.jiawa.train.common.exception.BusinessException;
import com.jiawa.train.common.exception.BusinessExceptionEnum;
import com.jiawa.train.common.util.SnowUtil;
import com.jiawa.train.member.domain.Member;
import com.jiawa.train.member.domain.MemberExample;
import com.jiawa.train.member.mapper.MemberMapper;
import com.jiawa.train.member.req.MemberRegisterReq;
import com.jiawa.train.member.req.MemberSenDCodeReq;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService {
    private static final Logger LOG = LoggerFactory.getLogger(MemberService.class);
    @Resource
    private MemberMapper mapper;
    public long count(){
        return mapper.countByExample(null);
    }
    public long register(MemberRegisterReq member){
        MemberExample example = new MemberExample();
        example.createCriteria().andMobileEqualTo(member.getMobile());
        List<Member> members = mapper.selectByExample(example);
        if (CollUtil.isNotEmpty(members)){
            throw new BusinessException(BusinessExceptionEnum.MEMBER_MOBILE_EXIST);
        }
        Member member1 = new Member();
        member1.setId(SnowUtil.getSnowFlakeNextId());
        member1.setMobile(member.getMobile());
        mapper.insert(member1);
        return member1.getId();
    }
    public void sendCode(MemberSenDCodeReq req){
        String mobile = req.getMobile();
        MemberExample example = new MemberExample();
        example.createCriteria().andMobileEqualTo(mobile);
        List<Member> members = mapper.selectByExample(example);
        if (CollUtil.isEmpty(members)){
            LOG.info("手机号不存在，插入一条记录");
           Member member = new Member();
           member.setId(SnowUtil.getSnowFlakeNextId());
           member.setMobile(mobile);
           mapper.insert(member);
        }
//        String code = RandomUtil.randomString(4);
        String code = "8888";
        LOG.info("生成短信验证码：{}",code);
    }
}
