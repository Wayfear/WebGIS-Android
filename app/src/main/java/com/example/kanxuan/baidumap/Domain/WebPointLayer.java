package com.example.kanxuan.baidumap.Domain;

import java.io.Serializable;

/**
 * Created by kanxuan on 2017/5/16.
 */

public abstract class WebLayer implements Serializable {

    private String layerId;

    public WebLayer() {
    }

    public WebLayer(String layerId) {
        this.layerId = layerId;
    }

    public String getLayerId() {
        return layerId;
    }

    public void setLayerId(String layerId) {
        this.layerId = layerId;
    }
}
