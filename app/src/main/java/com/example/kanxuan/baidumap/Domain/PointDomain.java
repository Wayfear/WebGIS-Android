package com.example.kanxuan.baidumap.Domain;

import com.example.kanxuan.baidumap.Enums.StatusEnum;

import java.io.Serializable;

/**
 * Created by kanxuan on 2017/5/16.
 */

public class PointDomain  implements Serializable {
    private String url;
    private String id;
    private double x;
    private double y;
    private double z;
    private StatusEnum status;
    public void setUrl(String url) {this.url = url;}

    public PointDomain() {
    }

    public PointDomain(double x, double y, double z, StatusEnum status) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.status = status;
    }

    public void setId(String id) {this.id = id;}

    public String getId() {return id;}

    public String getUrl() {return url;}

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "PointDomain{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                ", status=" + status +
                '}';
    }
}
