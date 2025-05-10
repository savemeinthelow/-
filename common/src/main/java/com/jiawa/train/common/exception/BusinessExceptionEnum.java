package com.jiawa.train.common.exception;

public enum BusinessExceptionEnum {
    MEMBER_MOBILE_EXIST("手机号已注册"),
    MEMBER_MOBILE_NOT_EXIST("请先获取短信认证"),
    MEMBER_MOBILE_CODE_ERROR("短信验证码错误"),
    BUSINESS_TRAIN_STATION_INDEX_UNIQUE_ERROR("火车站索引已存在"),
    BUSINESS_TRAIN_STATION_NAME_UNIQUE_ERROR("火车站名已存在"),

    BUSINESS_TRAIN_CODE_UNIQUE_ERROR("火车编号已存在"),
    CONFIRM_ORDER_TICKET_COUNT_ERROR("余票不足");

    private String desc;

    BusinessExceptionEnum(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "BusinessException{" +
                "desc='" + desc + '\'' +
                '}';
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
