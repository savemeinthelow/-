package com.jiawa.train.business.controller.admin;

import com.jiawa.train.business.req.TrainQueryReq;
import com.jiawa.train.business.req.TrainSaveReq;
import com.jiawa.train.business.resp.TrainQueryResp;
import com.jiawa.train.business.service.TrainService;
import com.jiawa.train.common.resp.CommonResp;
import com.jiawa.train.common.resp.PageResp;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/train")
public class TrainController {
    @Resource
    private TrainService service;

    @PostMapping("/save")
    public CommonResp save(@Valid @RequestBody TrainSaveReq train) {
        service.save(train);
        return new CommonResp<>();
    }
    @GetMapping("/query-list")
    public CommonResp<PageResp> queryList(@Valid  TrainQueryReq train) {
        PageResp<TrainQueryResp> pageResp = service.queryList(train);
        return new CommonResp<>(pageResp);
    }
    @DeleteMapping("/delete/{id}")
    public CommonResp delete(@PathVariable Long id){
        service.delete(id);
        return new CommonResp();
    }
    @GetMapping("/query-all")
    public CommonResp<List<TrainQueryResp>> queryList(){
        return new CommonResp(service.queryAll());
    }

}
