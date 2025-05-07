package com.jiawa.train.batch.job;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import com.jiawa.train.batch.feign.BusinessFeign;
import jakarta.annotation.Resource;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import java.util.Date;

public class DailyTrainJob implements Job {

    @Resource
    private BusinessFeign businessFeign;
    private static final Logger LOG = LoggerFactory.getLogger(DailyTrainJob.class);

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        MDC.put("LOG_ID", System.currentTimeMillis() + RandomUtil.randomString(3));
        LOG.info("开始执行定时任务生成每日数据");
        Date date = new Date();
        DateTime dateTime = DateUtil.offsetDay(date, 14);
        businessFeign.genDaily(dateTime.toJdkDate());
        LOG.info("结束执行定时任务");
    }


}
