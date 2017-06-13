package com.example.kanxuan.baidumap.Domain;

import com.example.kanxuan.baidumap.Enums.TypeEnum;

import java.io.Serializable;
import java.util.List;

/**
 * Created by kanxuan on 2017/6/4.
 */
public abstract class PipeDomain extends UpdateBaseDomain{

    private List<LineDomain> lineList;

    public PipeDomain() {
    }

    public PipeDomain(TypeEnum type, List<LineDomain> lineList) {
        super(type);
        this.lineList = lineList;
    }

    public List<LineDomain> getLineList() {
        return lineList;
    }

    public void setLineList(List<LineDomain> lineList) {
        this.lineList = lineList;
    }
}