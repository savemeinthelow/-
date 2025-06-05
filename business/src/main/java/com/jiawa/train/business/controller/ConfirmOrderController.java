package com.jiawa.train.business.controller;

import cn.hutool.core.util.ObjectUtil;
import com.jiawa.train.business.req.ConfirmOrderDoReq;
import com.jiawa.train.business.req.ConfirmOrderQueryReq;
import com.jiawa.train.business.resp.ConfirmOrderQueryResp;
import com.jiawa.train.business.service.BeforeConfirmOrderService;
import com.jiawa.train.business.service.ConfirmOrderService;
import com.jiawa.train.common.resp.CommonResp;
import com.jiawa.train.common.resp.PageResp;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/confirm-order")
public class ConfirmOrderController {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private ConfirmOrderService service;
    @Resource
    private BeforeConfirmOrderService beforeConfirmOrderService;

    private static final Logger LOG = LoggerFactory.getLogger(ConfirmOrderService.class);


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
        String imageCodeToken = req.getImageCodeToken();
        String imageCode = req.getImageCode();
        String imageCodeRedis = stringRedisTemplate.opsForValue().get(imageCodeToken);
        LOG.info("从redis中获取到的验证码：{}", imageCodeRedis);
        if (ObjectUtil.isEmpty(imageCodeRedis)){
            return new CommonResp<>(false,"验证码已过期",null);
        }
        if (imageCodeRedis != null && !imageCodeRedis.equalsIgnoreCase(imageCode)) {
            return new CommonResp<>(false, "验证码错误", null);
        }else{
            stringRedisTemplate.delete(imageCodeToken);
        }
//        service.doConfirm(req);
        beforeConfirmOrderService.beforeDoConfirm(req);
        return new CommonResp();

    }

}
