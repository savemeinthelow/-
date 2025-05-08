package com.jiawa.train.business.controller.admin;

import com.jiawa.train.business.req.DailyTrainQueryReq;
import com.jiawa.train.business.req.DailyTrainSaveReq;
import com.jiawa.train.business.resp.DailyTrainQueryResp;
import com.jiawa.train.business.service.DailyTrainService;
import com.jiawa.train.common.resp.CommonResp;
import com.jiawa.train.common.resp.PageResp;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/admin/daily-train")
public class DailyTrainAdminController {
    @Resource
    private DailyTrainService service;

    @PostMapping("/save")
    public CommonResp save(@Valid @RequestBody DailyTrainSaveReq dailyTrain) {
        service.save(dailyTrain);
        return new CommonResp<>();
    }
    @GetMapping("/query-list")
    public CommonResp<PageResp> queryList(@Valid  DailyTrainQueryReq dailyTrain) {
        PageResp<DailyTrainQueryResp> pageResp = service.queryList(dailyTrain);
        return new CommonResp<>(pageResp);
    }
    @DeleteMapping("/delete/{id}")
    public CommonResp delete(@PathVariable Long id){
        service.delete(id);
        return new CommonResp();
    }
    @GetMapping("/gen-daily/{date}")
    public CommonResp genDaily(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date date){
        service.genDaily(date);
        return new CommonResp();
    }
}
