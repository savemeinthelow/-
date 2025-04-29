package com.jiawa.train.${module}.controller;

import com.jiawa.train.common.context.LoginMemberContext;
import com.jiawa.train.common.resp.CommonResp;
import com.jiawa.train.common.resp.PageResp;
import com.jiawa.train.${module}.req.${Domain}QueryReq;
import com.jiawa.train.${module}.req.${Domain}SaveReq;
import com.jiawa.train.${module}.response.${Domain}QueryResp;
import com.jiawa.train.${module}.service.${Domain}Service;
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
        PageResp<${Domain}QueryResp> pageResp = service.queryList(${domain});
        return new CommonResp<>(pageResp);
    }
    @DeleteMapping("/delete/{id}")
    public CommonResp delete(@PathVariable Long id){
        service.delete(id);
        return new CommonResp();
    }

}
