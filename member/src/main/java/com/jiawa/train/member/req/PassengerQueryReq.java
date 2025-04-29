package com.jiawa.train.member.req;

import com.jiawa.train.common.req.PageReq;

public class PassengerQueryReq extends PageReq {
    private Long memberId;

    @Override
    public String toString() {
        return "PassengerQueryReq{" +
                "memberId=" + memberId +
                '}';
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }
}
