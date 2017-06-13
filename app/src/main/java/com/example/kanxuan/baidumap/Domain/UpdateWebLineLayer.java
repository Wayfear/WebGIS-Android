package com.example.kanxuan.baidumap.Domain;

/**
 * Created by kanxuan on 2017/6/4.
 */


public class UpdateWebLineLayer extends WebLayer {

    private CommonPipeDomain data;

    public UpdateWebLineLayer() {
    }

    public UpdateWebLineLayer(String layerId, CommonPipeDomain data) {
        super(layerId);
        this.data = data;
    }

    public CommonPipeDomain getData() {
        return data;
    }

    public void setData(CommonPipeDomain data) {
        this.data = data;
    }
}
