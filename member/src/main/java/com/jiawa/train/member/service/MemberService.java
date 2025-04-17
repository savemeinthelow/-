package com.jiawa.train.member.service;

import cn.hutool.core.collection.CollUtil;
import com.jiawa.train.member.domain.Member;
import com.jiawa.train.member.domain.MemberExample;
import com.jiawa.exception.BusinessException;
import com.jiawa.exception.BusinessExceptionEnum;
import com.jiawa.train.member.mapper.MemberMapper;
import com.jiawa.train.member.req.MemberRegisterReq;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService {
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
        member1.setId(System.currentTimeMillis());
        member1.setMobile(member.getMobile());
        mapper.insert(member1);
        return member1.getId();
    }
}
