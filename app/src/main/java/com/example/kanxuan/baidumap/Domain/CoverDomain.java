package com.example.kanxuan.baidumap.Domain;

import com.example.kanxuan.baidumap.Enums.TypeEnum;

import java.io.Serializable;
import java.util.List;

/**
 * Created by kanxuan on 2017/5/16.
 */

public class CoverDomain implements Serializable{

    private TypeEnum type;

    private List<PointDomain> pointList;

    public CoverDomain() {
    }

    public TypeEnum getType(){return type;}

    public CoverDomain(TypeEnum type, List<PointDomain> pointList) {
        this.type = type;
        this.pointList = pointList;
    }

    public List<PointDomain> getPointList() {
        return pointList;
    }

    public void setPointList(List<PointDomain> pointList) {
        this.pointList = pointList;
    }
}
