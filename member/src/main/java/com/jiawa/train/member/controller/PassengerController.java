package com.jiawa.train.member.controller;

import com.jiawa.train.common.context.LoginMemberContext;
import com.jiawa.train.common.response.CommonResp;
import com.jiawa.train.member.req.PassengerQueryReq;
import com.jiawa.train.member.req.PassengerSaveReq;
import com.jiawa.train.member.response.PassengerQueryResp;
import com.jiawa.train.member.service.PassengerService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/passenger")
public class PassengerController {
    @Resource
    private PassengerService service;

    @PostMapping("/save")
    public CommonResp save(@Valid @RequestBody PassengerSaveReq passenger) {
        service.save(passenger);
        return new CommonResp<>();
    }
    @GetMapping("/query-list")
    public CommonResp queryList(@Valid  PassengerQueryReq passenger) {
        passenger.setMemberId(LoginMemberContext.getId());
        List<PassengerQueryResp> passengerQueryResps = service.queryList(passenger);
        return new CommonResp<>(passengerQueryResps);
    }

}
