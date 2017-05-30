package com.example.kanxuan.baidumap.Domain;

import com.example.kanxuan.baidumap.Enums.StatusEnum;

/**
 * Created by kanxuan on 2017/5/16.
 */

public class PointDomain {
    private double x;
    private double y;
    private double z;
    private StatusEnum status;

    public PointDomain() {
    }

    public PointDomain(double x, double y, double z, StatusEnum status) {
        this.x = x;
        this.y = y;
        this.z = z;
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
