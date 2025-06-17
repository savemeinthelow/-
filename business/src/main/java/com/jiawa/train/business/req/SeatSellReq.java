package com.jiawa.train.business.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class SeatSellReq {

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "【日期】不能为空")
    private Date date;

    @NotBlank(message = "【车次编号】不能为空")
    private String trainCode;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTrainCode() {
        return trainCode;
    }

    public void setTrainCode(String trainCode) {
        this.trainCode = trainCode;
    }

    public SeatSellReq(Date date, String trainCode) {
        this.date = date;
        this.trainCode = trainCode;
    }
}
