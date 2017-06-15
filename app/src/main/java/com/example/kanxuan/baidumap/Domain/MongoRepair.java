package com.example.kanxuan.baidumap.Domain;

import com.example.kanxuan.baidumap.Enums.ReportStateEnum;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by kanxuan on 2017/6/15.
 */

public class MongoRepair implements Serializable {

    private String id;

    private Long specialId;
    private String layerId;
    private int userId;
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    private String desc;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getSpecialId() {
        return specialId;
    }

    public void setSpecialId(Long specialId) {
        this.specialId = specialId;
    }

    public String getLayerId() {
        return layerId;
    }

    public void setLayerId(String layerId) {
        this.layerId = layerId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }

    public ReportStateEnum getState() {
        return state;
    }

    public void setState(ReportStateEnum state) {
        this.state = state;
    }

    private long createDate;
        private ReportStateEnum state;


}
