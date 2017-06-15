package com.example.kanxuan.baidumap.Domain;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by kanxuan on 2017/6/15.
 */

public class MongoLayer implements Serializable {
    private String id;

    private MyBaseDomain data;
    private long createTime;
    private long updateTime;

    public MongoLayer() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public MyBaseDomain getData() {
        return data;
    }

    public void setData(MyBaseDomain data) {
        this.data = data;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }
}
