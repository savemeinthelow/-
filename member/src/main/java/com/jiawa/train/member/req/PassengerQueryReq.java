package com.jiawa.train.member.req;

public class PassengerQueryReq {
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
