package com.example.kanxuan.baidumap.Domain;

import com.example.kanxuan.baidumap.Enums.TypeEnum;

/**
 * Created by kanxuan on 2017/6/4.
 */


public abstract class UpdateBaseDomain {

    private TypeEnum type;

    public UpdateBaseDomain() {
    }

    public UpdateBaseDomain(TypeEnum type) {
        this.type = type;
    }

    public TypeEnum getType() {
        return type;
    }

    public void setType(TypeEnum type) {
        this.type = type;
    }
}