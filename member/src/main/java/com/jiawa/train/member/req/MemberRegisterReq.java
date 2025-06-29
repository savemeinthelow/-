package com.jiawa.train.member.req;

import jakarta.validation.constraints.NotBlank;

public class MemberRegisterReq {
    @NotBlank(message = "[手机号不能为空]")
    private String mobile;

    public MemberRegisterReq(String mobile) {
        this.mobile = mobile;
    }
    public MemberRegisterReq() {
    }
    @Override
    public String toString() {
        return "MemberRegisterReq{" +
                "mobile='" + mobile + '\'' +
                '}';
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
