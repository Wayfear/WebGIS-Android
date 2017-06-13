package com.example.kanxuan.baidumap.Domain;

import com.baidu.mapapi.model.LatLng;
import com.example.kanxuan.baidumap.Enums.StatusEnum;
import com.example.kanxuan.baidumap.Enums.TypeEnum;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by kanxuan on 2017/5/30.
 */

public class MapData {
        private TypeEnum type;
        private List<PointDomain> pointList = null;
        private List<LineDomain> lineList = null;
        private Date createtIime;
        private Date updateTime;

        public List<PointDomain> getPointList(){return pointList;}
        public List<LineDomain> getLineList() {return lineList;}

        public TypeEnum getType(){return type;}

        public MapData(LayerPointDate layerPointDate) {
            type = layerPointDate.getData().getType();
            pointList = layerPointDate.getData().getPointList();
            Timestamp ts1 = new Timestamp(Long.parseLong(layerPointDate.getCreateTime()));
            createtIime = new Date();
            createtIime = ts1;
            Timestamp ts2 = new Timestamp(Long.parseLong(layerPointDate.getUpdateTime()));
            updateTime = new Date();
            updateTime = ts2;
        }

        public MapData(LayerLineDate layerLineDate) {
            type = layerLineDate.getData().getType();
            lineList = layerLineDate.getData().getLineList();
            Timestamp ts1 = new Timestamp(Long.parseLong(layerLineDate.getCreateTime()));
            createtIime = new Date();
            createtIime = ts1;
            Timestamp ts2 = new Timestamp(Long.parseLong(layerLineDate.getUpdateTime()));
            updateTime = new Date();
            updateTime = ts2;
        }

        public Map<List<LatLng>, StatusEnum> ToDrawList() {
            if(lineList==null)
                return null;
            Map<List<LatLng>, StatusEnum> result = new HashMap<>();
            List<LatLng> latLngs = new ArrayList<>();
            StatusEnum status = lineList.get(0).getStatus();
            double tempx = -1, tempy = -1, tempz = -1;
            for(int i=0; i<lineList.size();i++) {
                double x1 = lineList.get(i).getX();
                double x2 = lineList.get(i).getX2();
                double y1 = lineList.get(i).getY();
                double y2 = lineList.get(i).getY2();
                double z1 = lineList.get(i).getZ();
                double z2 = lineList.get(i).getZ2();

                if(tempx==x1 && tempy == y1 && tempz == z1 && status==lineList.get(i).getStatus()){
                    latLngs.add(new LatLng(y2,x2));
                }
                else {
                    if(latLngs.size()!=0) {
                        result.put(latLngs, status);
                        status = lineList.get(i).getStatus();
                    }
                    latLngs = new ArrayList<>();
                    latLngs.add(new LatLng(y1,x1));
                    latLngs.add(new LatLng(y2,x2));
                }
                tempx = x2;
                tempy = y2;
                tempz = z2;
            }
            result.put(latLngs, status);
            return result;
        }


}
