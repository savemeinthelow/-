package com.jiawa.train.business.controller;

import com.jiawa.train.business.req.TrainSeatQueryReq;
import com.jiawa.train.business.resp.TrainSeatQueryResp;
import com.jiawa.train.business.service.TrainSeatService;
import com.jiawa.train.common.resp.CommonResp;
import com.jiawa.train.common.resp.PageResp;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/train-seat")
public class TrainSeatController {
    @Resource
    private TrainSeatService service;

    @GetMapping("/query-list")
    public CommonResp<PageResp> queryList(@Valid  TrainSeatQueryReq trainSeat) {
        PageResp<TrainSeatQueryResp> pageResp = service.queryList(trainSeat);
        return new CommonResp<>(pageResp);
    }

}
