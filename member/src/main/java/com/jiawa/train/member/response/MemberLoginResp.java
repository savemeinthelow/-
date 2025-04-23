package com.jiawa.train.member.response;

public class MemberLoginResp {
    private Long id;

    private String mobile;
    private String token;

    public MemberLoginResp(Long id, String mobile, String token) {
        this.id = id;
        this.mobile = mobile;
        this.token = token;
    }

    @Override
    public String toString() {
        return "MemberLoginResp{" +
                "id=" + id +
                ", mobile='" + mobile + '\'' +
                ", token='" + token + '\'' +
                '}';
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

}