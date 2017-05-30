package com.example.kanxuan.baidumap.Domain;

import com.example.kanxuan.baidumap.Enums.TypeEnum;

import java.io.Serializable;
import java.util.List;
import java.io.Serializable;
/**
 * Created by kanxuan on 2017/5/30.
 */

public class LinearDomain implements Serializable {
    private TypeEnum type;

    private List<LineDomain> lineList;

    public LinearDomain() {
    }

    public LinearDomain(TypeEnum type, List<LineDomain> lineList) {
        this.type = type;
        this.lineList = lineList;
    }

    public List<LineDomain> getLineList() {
        return lineList;
    }

    public TypeEnum getType() {return type;}

    public void setLineList(List<LineDomain> lineList) {
        this.lineList = lineList;
    }
}
