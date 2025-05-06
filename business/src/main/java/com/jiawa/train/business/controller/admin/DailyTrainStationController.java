package com.jiawa.train.business.controller.admin;

import com.jiawa.train.business.req.DailyTrainStationQueryReq;
import com.jiawa.train.business.req.DailyTrainStationSaveReq;
import com.jiawa.train.business.resp.DailyTrainStationQueryResp;
import com.jiawa.train.business.service.DailyTrainStationService;
import com.jiawa.train.common.resp.CommonResp;
import com.jiawa.train.common.resp.PageResp;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/daily-train-station")
public class DailyTrainStationController {
    @Resource
    private DailyTrainStationService service;

    @PostMapping("/save")
    public CommonResp save(@Valid @RequestBody DailyTrainStationSaveReq dailyTrainStation) {
        service.save(dailyTrainStation);
        return new CommonResp<>();
    }
    @GetMapping("/query-list")
    public CommonResp<PageResp> queryList(@Valid  DailyTrainStationQueryReq dailyTrainStation) {
        PageResp<DailyTrainStationQueryResp> pageResp = service.queryList(dailyTrainStation);
        return new CommonResp<>(pageResp);
    }
    @DeleteMapping("/delete/{id}")
    public CommonResp delete(@PathVariable Long id){
        service.delete(id);
        return new CommonResp();
    }

}
