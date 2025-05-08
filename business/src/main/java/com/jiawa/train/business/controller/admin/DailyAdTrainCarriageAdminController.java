package com.jiawa.train.business.controller.admin;

import com.jiawa.train.business.req.DailyTrainCarriageQueryReq;
import com.jiawa.train.business.req.DailyTrainCarriageSaveReq;
import com.jiawa.train.business.resp.DailyTrainCarriageQueryResp;
import com.jiawa.train.business.service.DailyTrainCarriageService;
import com.jiawa.train.common.resp.CommonResp;
import com.jiawa.train.common.resp.PageResp;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/daily-train-carriage")
public class DailyAdTrainCarriageAdminController {
    @Resource
    private DailyTrainCarriageService service;

    @PostMapping("/save")
    public CommonResp save(@Valid @RequestBody DailyTrainCarriageSaveReq dailyTrainCarriage) {
        service.save(dailyTrainCarriage);
        return new CommonResp<>();
    }
    @GetMapping("/query-list")
    public CommonResp<PageResp> queryList(@Valid  DailyTrainCarriageQueryReq dailyTrainCarriage) {
        PageResp<DailyTrainCarriageQueryResp> pageResp = service.queryList(dailyTrainCarriage);
        return new CommonResp<>(pageResp);
    }
    @DeleteMapping("/delete/{id}")
    public CommonResp delete(@PathVariable Long id){
        service.delete(id);
        return new CommonResp();
    }

}
