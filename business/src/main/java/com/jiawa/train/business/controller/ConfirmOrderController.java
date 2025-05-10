package com.jiawa.train.business.controller;

import com.jiawa.train.business.req.ConfirmOrderDoReq;
import com.jiawa.train.business.req.ConfirmOrderQueryReq;
import com.jiawa.train.business.resp.ConfirmOrderQueryResp;
import com.jiawa.train.business.service.ConfirmOrderService;
import com.jiawa.train.common.resp.CommonResp;
import com.jiawa.train.common.resp.PageResp;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/confirm-order")
public class ConfirmOrderController {
    @Resource
    private ConfirmOrderService service;

    @GetMapping("/query-list")
    public CommonResp<PageResp> queryList(@Valid  ConfirmOrderQueryReq confirmOrder) {
        PageResp<ConfirmOrderQueryResp> pageResp = service.queryList(confirmOrder);
        return new CommonResp<>(pageResp);
    }
    @DeleteMapping("/delete/{id}")
    public CommonResp delete(@PathVariable Long id){
        service.delete(id);
        return new CommonResp();
    }

    @PostMapping("/do")
    public CommonResp doConfirm(@RequestBody ConfirmOrderDoReq req){
        service.doConfirm(req);
        return new CommonResp();

    }

}
