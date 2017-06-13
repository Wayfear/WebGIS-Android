package com.example.kanxuan.baidumap.Domain;

import com.example.kanxuan.baidumap.Enums.StatusEnum;

import java.io.Serializable;

/**
 * Created by kanxuan on 2017/5/16.
 */

public class LineDomain  implements Serializable {
    private double x;
    private double y;
    private double z;

    private String url;
    private String id;

    private double x2;
    private double y2;
    private double z2;

    public void setUrl(String url) {this.url = url;}

    public String getUrl() {return url;}

    public void setId(String id) {this.id = id;}

    public String getId() {return id;}

    private StatusEnum status;

    public LineDomain() {
    }

    public LineDomain(double x, double y, double z, double x2, double y2, double z2, StatusEnum status) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.x2 = x2;
        this.y2 = y2;
        this.z2 = z2;
        this.status = status;
    }

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

    public double getX2() {
        return x2;
    }

    public void setX2(double x2) {
        this.x2 = x2;
    }

    public double getY2() {
        return y2;
    }

    public void setY2(double y2) {
        this.y2 = y2;
    }

    public double getZ2() {
        return z2;
    }

    public void setZ2(double z2) {
        this.z2 = z2;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "LineDomain{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                ", x2=" + x2 +
                ", y2=" + y2 +
                ", z2=" + z2 +
                ", status=" + status +
                '}';
    }
}
