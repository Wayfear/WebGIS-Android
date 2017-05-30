package com.example.kanxuan.baidumap.Http;

/**
 * Created by kanxuan on 2017/5/9.
 */

public class Fault extends RuntimeException {
    private int errorCode;

    public Fault(int errorCode,String message){
        super(message);
        errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }
}
