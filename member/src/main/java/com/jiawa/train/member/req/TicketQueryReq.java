package com.jiawa.train.member.req;

import com.jiawa.train.common.req.PageReq;

public class TicketQueryReq extends PageReq {
    private long memberId;

    public long getMemberId() {
        return memberId;
    }

    public void setMemberId(long memberId) {
        this.memberId = memberId;
    }

    @Override
    public String toString() {
        return "TicketQueryReq{" +
                "} " + super.toString();
    }
}
