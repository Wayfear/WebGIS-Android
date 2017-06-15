package com.example.kanxuan.baidumap.Domain;

import com.example.kanxuan.baidumap.Enums.StatusEnum;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by kanxuan on 2017/5/16.
 */

public class PointDomain  implements Serializable {
    private String url;
    private String pointId;
    private Long specialId;

    private double x;
    private double y;
    private double z;

    public String getPointId() {
        return pointId;
    }

    public void setPointId(String pointId) {
        this.pointId = pointId;
    }

    private StatusEnum status;

    private List<String> repairIds;
    private List<MongoRepair> repairs;

    public PointDomain() {
        this.repairIds = new ArrayList<>();
        this.repairs = new ArrayList<>();
    }

    public PointDomain(double x, double y, double z, StatusEnum status, String url) {
        this.url = url;
        pointId = "";
        this.x = x;
        this.y = y;
        this.z = z;
        this.status = status;
        this.specialId = new Date().getTime();
        this.repairIds = new ArrayList<>();
    }

    public List<String> getRepairIds() {
        return repairIds;
    }

    public List<MongoRepair> getRepairs() {
        return repairs;
    }

    public void setRepairs(List<MongoRepair> repairs) {
        this.repairs = repairs;
    }

    public void setRepairIds(List<String> repairIds) {
        this.repairIds = repairIds;
    }

    public Long getSpecialId() {
        return specialId;
    }

    public void setSpecialId(Long specialId) {
        this.specialId = specialId;
    }



    public String getUrl() {return url;}

    public void setUrl(String url) {
        this.url = url;
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
