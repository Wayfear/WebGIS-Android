package com.example.kanxuan.baidumap.Domain;

import java.io.Serializable;

/**
 * Created by kanxuan on 2017/6/15.
 */

public class WebRepair implements Serializable {

    private String id;
    private Long specialId;
    private String layerId;
    private int userId;

    private String url;
    private String desc;
    private String state;

    private PointDomain point;
    private LineDomain line;

    public WebRepair(){}

    public WebRepair(MongoRepair repair) {
        this.id = repair.getId();
        this.specialId = repair.getSpecialId();
        this.layerId = repair.getLayerId();
        this.userId = repair.getUserId();
        this.url = repair.getUrl();
        this.desc = repair.getDesc();
        this.state = repair.getState().getValue();
    }

    public PointDomain getPoint() {
        return point;
    }

    public void setPoint(PointDomain point) {
        this.point = point;
    }

    public LineDomain getLine() {
        return line;
    }

    public void setLine(LineDomain line) {
        this.line = line;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}