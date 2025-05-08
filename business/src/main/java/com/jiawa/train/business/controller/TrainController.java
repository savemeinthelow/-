package com.jiawa.train.business.controller;

import com.jiawa.train.business.resp.TrainQueryResp;
import com.jiawa.train.business.service.TrainSeatService;
import com.jiawa.train.business.service.TrainService;
import com.jiawa.train.common.resp.CommonResp;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/train")
public class TrainController {
    @Resource
    private TrainService service;
    @Resource
    private TrainSeatService seatService;
    @GetMapping("/query-all")
    public CommonResp<List<TrainQueryResp>> queryList(){
        return new CommonResp(service.queryAll());
    }
    @GetMapping("/gen-seat/{trainCode}")
    public CommonResp genSeat(@PathVariable String trainCode){
        seatService.genTrainSeat(trainCode);
        return new CommonResp();
    }

}
