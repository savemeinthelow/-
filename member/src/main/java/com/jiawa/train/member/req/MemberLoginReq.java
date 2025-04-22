package com.jiawa.train.member.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class MemberLoginReq {
    @NotBlank(message = "[手机号不能为空]")
    @Pattern(regexp = "^1\\d{10}$",message = "手机号格式错误")
    private String mobile;
    @NotBlank(message = "【短信验证码】不能为空")
    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public MemberLoginReq(String mobile, String code) {
        this.mobile = mobile;
        this.code = code;
    }   public MemberLoginReq() {
    }

    @Override
    public String toString() {
        return "MemberLoginReq{" +
                "mobile='" + mobile + '\'' +
                ", code='" + code + '\'' +
                '}';
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
