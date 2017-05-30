package com.example.kanxuan.baidumap.Domain;

import java.io.Serializable;

/**
 * Created by kanxuan on 2017/5/30.
 */

public class LayerLineDate implements Serializable{
    private String id;

    private LinearDomain data;
    private String createTime;
    private String updateTime;

    public LayerLineDate(){}

    public LayerLineDate(LinearDomain data, String createTime, String updateTime) {
        this.id = id;
        this.data = data;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }


    public LayerLineDate(String id, LinearDomain data, String createTime, String updateTime) {
        this.id = id;
        this.data = data;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LinearDomain getData() {
        return data;
    }

    public void setData(LinearDomain data) {
        this.data = data;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}
