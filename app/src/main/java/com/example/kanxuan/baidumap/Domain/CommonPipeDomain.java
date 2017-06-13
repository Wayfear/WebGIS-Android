package com.example.kanxuan.baidumap.Domain;

import com.example.kanxuan.baidumap.Enums.TypeEnum;

import java.util.List;

/**
 * Created by kanxuan on 2017/6/4.
 */

public class CommonPipeDomain extends PipeDomain {

    public CommonPipeDomain() {
    }

    public CommonPipeDomain(List<LineDomain> lineList) {
        super(TypeEnum.XSG, lineList);
    }

}