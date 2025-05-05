package com.jiawa.train.batch.controller;

import cn.hutool.core.util.RandomUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/hello")
    public String hello() throws InterruptedException {
        int i = RandomUtil.randomInt(1, 10);
        if (i <= 2) {
            throw new RuntimeException("测试异常");
        }
        return "Hello World! Batch!";
    }


}
