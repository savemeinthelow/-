package com.jiawa.train.business.controller;

import com.jiawa.train.business.req.TrainCarriageQueryReq;
import com.jiawa.train.business.resp.TrainCarriageQueryResp;
import com.jiawa.train.business.service.TrainCarriageService;
import com.jiawa.train.common.resp.CommonResp;
import com.jiawa.train.common.resp.PageResp;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/train-carriage")
public class TrainCarriageController {
    @Resource
    private TrainCarriageService service;

    @GetMapping("/query-list")
    public CommonResp<PageResp> queryList(@Valid  TrainCarriageQueryReq trainCarriage) {
        PageResp<TrainCarriageQueryResp> pageResp = service.queryList(trainCarriage);
        return new CommonResp<>(pageResp);
    }

}
