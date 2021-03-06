package com.example.kanxuan.baidumap.Http;

import android.util.Log;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;


/**
 * Created by kanxuan on 2017/5/9.
 */

public class HttpCommonInterceptor implements Interceptor {
    private Map<String, String> headerParamsMap = new HashMap<>();

    public HttpCommonInterceptor(){}

    @Override
    public Response intercept(Chain chain) throws IOException {
        Log.d("HttpCommonInterceptor","add common params");
        Request oldRequest = chain.request();
        // 添加新的参数，添加到url 中
     /* HttpUrl.Builder authorizedUrlBuilder = oldRequest.url()                .newBuilder()
         .scheme(oldRequest.url().scheme())
             .host(oldRequest.url().host());*/
        // 新的请求
        Request.Builder requestBuilder =  oldRequest.newBuilder();
        requestBuilder.method(oldRequest.method(),
                oldRequest.body());

        //添加公共参数,添加到header中
        if(headerParamsMap.size() > 0){
            for(Map.Entry<String,String> params:headerParamsMap.entrySet()){
                requestBuilder.header(params.getKey(),params.getValue());
            }
        }
        Request newRequest = requestBuilder.build();
        return chain.proceed(newRequest);
    }
    public static class Builder{
        HttpCommonInterceptor httpCommonInterceptor;
        public Builder(){
            httpCommonInterceptor = new HttpCommonInterceptor();
        }
        public Builder addHeaderParams(String key, String value){
            httpCommonInterceptor.headerParamsMap.put(key,value);
            return this;
        }
        public Builder  addHeaderParams(String key, int value){
            return addHeaderParams(key, String.valueOf(value));
        }
        public Builder  addHeaderParams(String key, float value){
            return addHeaderParams(key, String.valueOf(value));
        }
        public Builder  addHeaderParams(String key, long value){
            return addHeaderParams(key, String.valueOf(value));
        }
        public Builder  addHeaderParams(String key, double value){
            return addHeaderParams(key, String.valueOf(value));
        }
        public HttpCommonInterceptor build(){
            return httpCommonInterceptor;
        }
    }

}
