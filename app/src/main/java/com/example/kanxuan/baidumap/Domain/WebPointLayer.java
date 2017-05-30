package com.example.kanxuan.baidumap.Domain;

import java.io.Serializable;
import java.util.List;

/**
 * Created by kanxuan on 2017/5/16.
 */

public class WebPointLayer implements Serializable {

    private String id;

    private List<PointDomain> data;

    public WebPointLayer() {
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
