package com.example.kanxuan.baidumap.HttpLoader;

import com.example.kanxuan.baidumap.Domain.AuthenticationRequest;
import com.example.kanxuan.baidumap.Http.BaseResponse;

import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by kanxuan on 2017/5/16.
 */

public class AccountLoader {

    public interface AccountService {
        @POST("account/sessions")
        Observable<BaseResponse<String>> getMapByID(@Body AuthenticationRequest authenticationRequest);
    }

}
