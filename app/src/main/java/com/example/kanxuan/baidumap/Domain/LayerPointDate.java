package com.example.kanxuan.baidumap.Domain;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by kanxuan on 2017/5/17.
 */

public class LayerPointDate implements Serializable {

    private String id;

    private CoverDomain data;
    private String createTime;
    private String updateTime;

    public LayerPointDate(){}

    public LayerPointDate(CoverDomain data, String createTime, String updateTime) {
        this.id = id;
        this.data = data;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }


    public LayerPointDate(String id, CoverDomain data, String createTime, String updateTime) {
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

    public CoverDomain getData() {
        return data;
    }

    public void setData(CoverDomain data) {
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