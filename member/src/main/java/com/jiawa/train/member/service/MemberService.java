package com.jiawa.train.member.service;

import cn.hutool.core.collection.CollUtil;
import com.jiawa.train.member.domain.Member;
import com.jiawa.train.member.domain.MemberExample;
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
            throw new RuntimeException("手机号已被注册");
        }
        Member member1 = new Member();
        member1.setId(System.currentTimeMillis());
        member1.setMobile(member.getMobile());
        mapper.insert(member1);
        return member1.getId();
    }
}
