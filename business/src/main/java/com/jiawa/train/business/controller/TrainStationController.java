package com.jiawa.train.business.controller;

import com.jiawa.train.business.req.TrainStationQueryReq;
import com.jiawa.train.business.resp.TrainStationQueryResp;
import com.jiawa.train.business.service.TrainStationService;
import com.jiawa.train.common.resp.CommonResp;
import com.jiawa.train.common.resp.PageResp;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/train-station")
public class TrainStationController {
    @Resource
    private TrainStationService service;

    @GetMapping("/query-list")
    public CommonResp<PageResp> queryList(@Valid  TrainStationQueryReq trainStation) {
        PageResp<TrainStationQueryResp> pageResp = service.queryList(trainStation);
        return new CommonResp<>(pageResp);
    }

}
