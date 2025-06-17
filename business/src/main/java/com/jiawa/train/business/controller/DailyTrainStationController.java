package com.jiawa.train.business.controller;

import com.jiawa.train.business.req.DailyTrainStationQueryAllReq;
import com.jiawa.train.business.req.DailyTrainStationQueryReq;
import com.jiawa.train.business.resp.DailyTrainStationQueryResp;
import com.jiawa.train.business.service.DailyTrainStationService;
import com.jiawa.train.common.resp.CommonResp;
import com.jiawa.train.common.resp.PageResp;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.bouncycastle.cert.ocsp.Req;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/daily-train-station")
public class DailyTrainStationController {
    @Resource
    private DailyTrainStationService service;


    @GetMapping("/query-list")
    public CommonResp<PageResp> queryList(@Valid DailyTrainStationQueryReq dailyTrainStation) {
        PageResp<DailyTrainStationQueryResp> pageResp = service.queryList(dailyTrainStation);
        return new CommonResp<>(pageResp);
    }

    @GetMapping("/daily-train-station")
    public CommonResp<List<DailyTrainStationQueryResp>> queryByTrain(@Valid DailyTrainStationQueryAllReq req) {
        List<DailyTrainStationQueryResp> dailyTrainStationQueryResps = service.queryByTrain(req.getDate(), req.getTrainCode());
        return new CommonResp<>(dailyTrainStationQueryResps);
    }

}
