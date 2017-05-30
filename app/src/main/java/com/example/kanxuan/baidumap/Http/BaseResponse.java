package com.example.kanxuan.baidumap.Http;

/**
 * Created by kanxuan on 2017/5/9.
 */

public class BaseResponse<T> {
    public int code;
    public String message;
    public T data;

    public boolean isSuccess(){
        return code == 200;
    }
}
