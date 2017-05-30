package com.example.kanxuan.baidumap.Domain;

import java.util.List;

/**
 * Created by kanxuan on 2017/5/17.
 */

public class WebMapContent{

    private int mapId;
    private List<LayerPointDate> layerList;

    public WebMapContent() {
    }

    public WebMapContent(int mapId, List layerList) {
        this.mapId = mapId;
        this.layerList = layerList;
    }

    public int getMapId() {
        return mapId;
    }

    public void setMapId(int mapId) {
        this.mapId = mapId;
    }

    public List getLayerList() {
        return layerList;
    }

    public void setLayerList(List layerList) {
        this.layerList = layerList;
    }
}
