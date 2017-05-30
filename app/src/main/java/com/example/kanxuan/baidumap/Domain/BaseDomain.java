package com.example.kanxuan.baidumap.Domain;

import com.example.kanxuan.baidumap.Enums.TypeEnum;

import java.io.Serializable;

/**
 * Created by kanxuan on 2017/5/16.
 */

public class BaseDomain implements Serializable {

    private TypeEnum type;

    private String id;

    public BaseDomain() {

    }

    public BaseDomain(TypeEnum type) {
        this.type = type;
    }


    public BaseDomain(String id, TypeEnum type) {
        this.id = id;
        this.type = type;
    }

    public TypeEnum getType() {
        return type;
    }

    public void setType(TypeEnum type) {
        this.type = type;
    }

    public String getId() {return id;}

    public void setId(String id) {
        this.id = id;
    }

}