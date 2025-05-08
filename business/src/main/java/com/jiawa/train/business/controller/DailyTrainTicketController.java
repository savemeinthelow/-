package com.jiawa.train.business.controller;

import com.jiawa.train.business.req.DailyTrainTicketQueryReq;
import com.jiawa.train.business.resp.DailyTrainTicketQueryResp;
import com.jiawa.train.business.service.DailyTrainTicketService;
import com.jiawa.train.common.resp.CommonResp;
import com.jiawa.train.common.resp.PageResp;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/daily-train-ticket")
public class DailyTrainTicketController {
    @Resource
    private DailyTrainTicketService service;

    @GetMapping("/query-list")
    public CommonResp<PageResp> queryList(@Valid  DailyTrainTicketQueryReq dailyTrainTicket) {
        PageResp<DailyTrainTicketQueryResp> pageResp = service.queryList(dailyTrainTicket);
        return new CommonResp<>(pageResp);
    }

}
