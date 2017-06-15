package com.example.kanxuan.baidumap.Enums;

/**
 * Created by kanxuan on 2017/5/16.
 */

public enum  StatusEnum {
    LOST(0, "丢失"),
    BREAK(1, "破坏"),
    BLOCK(2, "故障"),
    GOOD(3, "完好"),
    SERVICE(4, "维修");


    private int code;
    private String value;

    StatusEnum(int code, String value) {
        this.code = code;
        this.value = value;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public static StatusEnum getEnum(String status) {
        for (StatusEnum statusEnum : StatusEnum.values()) {
            if (statusEnum.name().equals(status)) {
                return statusEnum;
            }
        }
        return null;
    }

    public static StatusEnum getEnumByNum(int index) {
        for (StatusEnum statusEnum : StatusEnum.values()) {
            if (statusEnum.getCode()==index) {
                return statusEnum;
            }
        }
        return null;
    }
}
