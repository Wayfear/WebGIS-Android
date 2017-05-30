package com.example.kanxuan.baidumap.Domain;

import com.baidu.mapapi.model.LatLng;
import com.example.kanxuan.baidumap.Enums.TypeEnum;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

        public List<List<LatLng>> ToDrawList() {
            if(lineList==null)
                return null;
            List<List<LatLng>> result = new ArrayList<>();
            List<LatLng> latLngs = new ArrayList<>();
            double tempx = -1, tempy = -1, tempz = -1;
            for(int i=0; i<lineList.size();i++) {
                double x1 = lineList.get(i).getX();
                double x2 = lineList.get(i).getX2();
                double y1 = lineList.get(i).getY();
                double y2 = lineList.get(i).getY2();
                double z1 = lineList.get(i).getZ();
                double z2 = lineList.get(i).getZ2();

                if(tempx==x1 && tempy == y1 && tempz == z1){
                    latLngs.add(new LatLng(x2,y2));
                }
                else {
                    if(latLngs.size()!=0)
                        result.add(latLngs);
                    latLngs = new ArrayList<>();
                    latLngs.add(new LatLng(x1,y1));
                    latLngs.add(new LatLng(x2,y2));
                }
                tempx = x2;
                tempy = y2;
                tempz = z2;
            }
            return result;
        }


}
