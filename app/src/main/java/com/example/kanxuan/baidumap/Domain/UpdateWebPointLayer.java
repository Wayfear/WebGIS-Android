package com.example.kanxuan.baidumap.Domain;

/**
 * Created by kanxuan on 2017/6/4.
 */

public class UpdateWebPointLayer extends WebLayer {

    private CommonCoverDomain data;

    public UpdateWebPointLayer() {
    }

    public UpdateWebPointLayer(String layerId, CommonCoverDomain data) {
        super(layerId);
        this.data = data;
    }

    public CommonCoverDomain getData() {
        return data;
    }

    public void setData(CommonCoverDomain data) {
        this.data = data;
    }
}
