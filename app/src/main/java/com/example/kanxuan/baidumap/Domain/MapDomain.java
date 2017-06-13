package com.example.kanxuan.baidumap.Domain;

import java.io.Serializable;
import java.util.List;

/**
 * Created by kanxuan on 2017/5/9.
 */

public class MapDomain implements Serializable {

    private Integer account_id;
    private String name;
    private Integer folder;
    private String description;
    private List<String> layerIds;
    private int id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAccount_id() {
        return account_id;
    }

    public String getName() {
        return name;
    }

    public Integer getFolder() {
        return folder;
    }

    public String getDescription() {
        return description;
    }

    public List<String> getLayerIds() {
        return layerIds;
    }


}
