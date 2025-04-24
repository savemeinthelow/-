package com.jiawa.train.member.controller;

import com.jiawa.train.common.response.CommonResp;
import com.jiawa.train.member.req.PassengerSaveReq;
import com.jiawa.train.member.service.PassengerService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
