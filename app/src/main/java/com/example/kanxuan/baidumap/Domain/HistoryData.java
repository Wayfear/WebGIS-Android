package com.example.kanxuan.baidumap.Domain;

import java.io.Serializable;
import java.util.List;

/**
 * Created by kanxuan on 2017/5/30.
 */

public class HistoryData implements Serializable {

    private String date;

    private String description;

    private String id;

    private List<BaseDomain> data;

    public String getDate() {return date;}

    public List<BaseDomain> getData() {return data;}

    public HistoryData() {}

    public HistoryData(List<BaseDomain> maps) {
        data = maps;
        date =  "现在的图层";
    }
}
