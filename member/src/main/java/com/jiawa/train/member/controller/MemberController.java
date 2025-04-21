package com.jiawa.train.member.controller;

import com.jiawa.train.common.response.CommonResp;
import com.jiawa.train.member.req.MemberRegisterReq;
import com.jiawa.train.member.req.MemberSenDCodeReq;
import com.jiawa.train.member.service.MemberService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/member")
public class MemberController {
    @Resource
    private MemberService service;
    @GetMapping("/hello")
    public String hello(){
        return "HelloWorld421414";
    }

    @GetMapping("/count")
    public CommonResp<Long> count(){
        CommonResp<Long> resp = new CommonResp<>();
        resp.setContent(service.count());
        return resp;
    }
    @PostMapping("/register")
    public CommonResp<Long> register(@Valid MemberRegisterReq member){
        CommonResp<Long> resp = new CommonResp<>();
        resp.setContent(service.register(member));
       return resp;
    }
    @PostMapping("/send-code")
    public CommonResp<Long> sendCode(@Valid MemberSenDCodeReq member){
        service.sendCode(member);
        return new CommonResp<>();
    }
}
