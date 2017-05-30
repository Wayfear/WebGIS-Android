package com.example.kanxuan.baidumap.Http;

import rx.functions.Func1;

/**
 * Created by kanxuan on 2017/5/9.
 */

public class PayLoad<T> implements Func1<BaseResponse<T>,T>{
    @Override
    public T call(BaseResponse<T> tBaseResponse) {//获取数据失败时，包装一个Fault 抛给上层处理错误
        if(!tBaseResponse.isSuccess()){
            throw new Fault(tBaseResponse.code,tBaseResponse.message);
        }
        return tBaseResponse.data;
    }
}
