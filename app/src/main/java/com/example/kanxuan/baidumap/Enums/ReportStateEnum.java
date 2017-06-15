package com.example.kanxuan.baidumap.Enums;

/**
 * Created by kanxuan on 2017/6/15.
 */

public enum ReportStateEnum {
    DAMAGE("0","损坏"),
    LOST("1","丢失"),
    BLOCK("2","堵塞"),
    FINISH("3","已处理");

    private String code;
    private String value;

    ReportStateEnum(String code, String value) {
        this.code = code;
        this.value = value;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public static ReportStateEnum getEnum(String type) {
        for (ReportStateEnum stateEnum : ReportStateEnum.values()) {
            if (stateEnum.name().equals(type)) {
                return stateEnum;
            }
        }
        return null;
    }
}
