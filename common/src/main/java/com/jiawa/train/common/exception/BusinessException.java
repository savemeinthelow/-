package com.jiawa.train.common.exception;

public class BusinessException extends RuntimeException{
    private BusinessExceptionEnum e;

    public BusinessException(BusinessExceptionEnum e) {
        this.e = e;
    }

    @Override
    public String toString() {
        return "BusinessException{" +
                "e=" + e +
                '}';
    }

    public BusinessExceptionEnum getE() {
        return e;
    }

    public void setE(BusinessExceptionEnum e) {
        this.e = e;
    }

    @Override
    public Throwable fillInStackTrace(){
        return this;
    }
}
