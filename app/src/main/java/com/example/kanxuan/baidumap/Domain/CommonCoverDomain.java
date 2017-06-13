package com.example.kanxuan.baidumap.Domain;

import com.example.kanxuan.baidumap.Enums.TypeEnum;

import java.util.List;

/**
 * Created by kanxuan on 2017/6/4.
 */


public class CommonCoverDomain extends CoverDomain {

    public CommonCoverDomain() {
    }

    public CommonCoverDomain(List<PointDomain> pointList) {
        super(TypeEnum.YJG, pointList);
    }

}
