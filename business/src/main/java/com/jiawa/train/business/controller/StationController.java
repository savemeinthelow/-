package com.jiawa.train.business.controller;

import com.jiawa.train.business.req.StationQueryReq;
import com.jiawa.train.business.req.StationSaveReq;
import com.jiawa.train.business.resp.StationQueryResp;
import com.jiawa.train.business.service.StationService;
import com.jiawa.train.common.resp.CommonResp;
import com.jiawa.train.common.resp.PageResp;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/station")
public class StationController {
    @Resource
    private StationService service;

    @PostMapping("/save")
    public CommonResp save(@Valid @RequestBody StationSaveReq station) {
        service.save(station);
        return new CommonResp<>();
    }
    @GetMapping("/query-list")
    public CommonResp<PageResp> queryList(@Valid  StationQueryReq station) {
        PageResp<StationQueryResp> pageResp = service.queryList(station);
        return new CommonResp<>(pageResp);
    }
    @DeleteMapping("/delete/{id}")
    public CommonResp delete(@PathVariable Long id){
        service.delete(id);
        return new CommonResp();
    }

}
