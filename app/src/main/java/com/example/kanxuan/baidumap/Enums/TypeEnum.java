package com.example.kanxuan.baidumap.Enums;

/**
 * Created by kanxuan on 2017/5/16.
 */

public enum TypeEnum {
    YJG("001", "窨井盖"),
    XSG("002", "下水管");

    private String code;
    private String value;

    TypeEnum(String code, String value) {
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

    public static TypeEnum getEnum(String type) {
        for (TypeEnum typeEnum : TypeEnum.values()) {
            if (typeEnum.name().equals(type)) {
                return typeEnum;
            }
        }
        return null;
    }
}
