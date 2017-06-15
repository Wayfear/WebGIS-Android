package com.example.kanxuan.baidumap.Domain;

import com.example.kanxuan.baidumap.Enums.TypeEnum;

import java.io.Serializable;
import java.util.List;

/**
 * Created by kanxuan on 2017/6/15.
 */

public class MyBaseDomain implements Serializable{
    private TypeEnum type;

    public List<PointDomain> getPointList() {
        return pointList;
    }

    public void setPointList(List<PointDomain> pointList) {
        this.pointList = pointList;
    }

    public List<LineDomain> getLineList() {
        return lineList;
    }

    public void setLineList(List<LineDomain> lineList) {
        this.lineList = lineList;
    }

    private List<PointDomain> pointList;

    private List<LineDomain> lineList;

    public TypeEnum getType() {
        return type;
    }

    public void setType(TypeEnum type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String id;

}
