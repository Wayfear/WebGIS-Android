package com.example.kanxuan.baidumap.Domain;

import com.example.kanxuan.baidumap.Enums.StatusEnum;
import com.example.kanxuan.baidumap.Enums.TypeEnum;

import java.io.Serializable;

/**
 * Created by kanxuan on 2017/6/5.
 */

public class Information implements Serializable{

    private int index;
    private TypeEnum type;

    private StatusEnum status;

    public Information(LineDomain lineDomain) {
        type = TypeEnum.XSG;
        status = lineDomain.getStatus();
    }

    public Information(PointDomain pointDomain, int index) {
        type = TypeEnum.YJG;
        status = pointDomain.getStatus();
        this.index = index;
    }

    public Information(TypeEnum type, StatusEnum status, int index) {
        this.type = type;
        this.status = status;
        this.index = index;
    }

    public StatusEnum getStatus() {return status;}

    public void setStatus(StatusEnum status) {this.status= status;}

    public TypeEnum getType() {
        return type;
    }

    public int getIndex() {return index; }

    public void setIndex(int index) {this.index = index;}

}
