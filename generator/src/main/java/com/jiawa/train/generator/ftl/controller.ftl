package com.jiawa.train.member.controller;

import com.jiawa.train.common.context.LoginMemberContext;
import com.jiawa.train.common.response.CommonResp;
import com.jiawa.train.common.response.PageResp;
import com.jiawa.train.member.req.${Domain}QueryReq;
import com.jiawa.train.member.req.${Domain}SaveReq;
import com.jiawa.train.member.response.${Domain}QueryResp;
import com.jiawa.train.member.service.${Domain}Service;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/${do_main}")
public class ${Domain}Controller {
    @Resource
    private ${Domain}Service service;

    @PostMapping("/save")
    public CommonResp save(@Valid @RequestBody ${Domain}SaveReq ${domain}) {
        service.save(${domain});
        return new CommonResp<>();
    }
    @GetMapping("/query-list")
    public CommonResp<PageResp> queryList(@Valid  ${Domain}QueryReq ${domain}) {
        ${domain}.setMemberId(LoginMemberContext.getId());
        PageResp<${Domain}QueryResp> pageResp = service.queryList(${domain});
        return new CommonResp<>(pageResp);
    }
    @DeleteMapping("/delete/{id}")
    public CommonResp delete(@PathVariable Long id){
        service.delete(id);
        return new CommonResp();
    }

}
