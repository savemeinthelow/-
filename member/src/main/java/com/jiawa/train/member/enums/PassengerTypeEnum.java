package com.jiawa.train.member.enums;

public enum PassengerTypeEnum {
    ADULT("1","成年人"),
    CHILD("2","儿童"),
        STUDENT("3","学生");
    private String code;
    private String desc;

    PassengerTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }


    @Override
    public String toString() {
        return "PassengerTypeEnum{" +
                "code='" + code + '\'' +
                ", desc='" + desc + '\'' +
                '}';
    }
}
