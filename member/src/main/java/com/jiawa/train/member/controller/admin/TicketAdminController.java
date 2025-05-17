package com.jiawa.train.member.controller.admin;

import com.jiawa.train.common.resp.CommonResp;
import com.jiawa.train.common.resp.PageResp;
import com.jiawa.train.member.req.TicketQueryReq;
import com.jiawa.train.member.resp.TicketQueryResp;
import com.jiawa.train.member.service.TicketService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/ticket")
public class TicketAdminController {
    @Resource
    private TicketService service;

    @GetMapping("/query-list")
    public CommonResp<PageResp> queryList(@Valid  TicketQueryReq ticket) {
        PageResp<TicketQueryResp> pageResp = service.queryList(ticket);
        return new CommonResp<>(pageResp);
    }

}
