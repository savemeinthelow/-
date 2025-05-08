package com.jiawa.train.business.controller.admin;

import com.jiawa.train.business.req.DailyTrainSeatQueryReq;
import com.jiawa.train.business.req.DailyTrainSeatSaveReq;
import com.jiawa.train.business.resp.DailyTrainSeatQueryResp;
import com.jiawa.train.business.service.DailyTrainSeatService;
import com.jiawa.train.common.resp.CommonResp;
import com.jiawa.train.common.resp.PageResp;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/daily-train-seat")
public class DailyTrainSeatAdminController {
    @Resource
    private DailyTrainSeatService service;

    @PostMapping("/save")
    public CommonResp save(@Valid @RequestBody DailyTrainSeatSaveReq dailyTrainSeat) {
        service.save(dailyTrainSeat);
        return new CommonResp<>();
    }
    @GetMapping("/query-list")
    public CommonResp<PageResp> queryList(@Valid  DailyTrainSeatQueryReq dailyTrainSeat) {
        PageResp<DailyTrainSeatQueryResp> pageResp = service.queryList(dailyTrainSeat);
        return new CommonResp<>(pageResp);
    }
    @DeleteMapping("/delete/{id}")
    public CommonResp delete(@PathVariable Long id){
        service.delete(id);
        return new CommonResp();
    }

}
