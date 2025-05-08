package com.jiawa.train.business.req;

import com.jiawa.train.common.req.PageReq;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class DailyTrainTicketQueryReq extends PageReq {
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;
    private String trainCode;
    private String start;
    private String end;

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

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


    @Override
    public String toString() {
        return "DailyTrainTicketQueryReq{" +
                "} " + super.toString();
    }
}
