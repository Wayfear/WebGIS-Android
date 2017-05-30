package com.example.kanxuan.baidumap.Domain;

import java.io.Serializable;
import java.util.List;

/**
 * Created by kanxuan on 2017/5/16.
 */

public class SerilzeData<T> implements Serializable
{
    List<T> data;

    public SerilzeData(List<T> da) {
        data = da;
    }

    public List<T> getData(){
        return data;
    }
}