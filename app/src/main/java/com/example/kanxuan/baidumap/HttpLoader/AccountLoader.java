package com.example.kanxuan.baidumap.HttpLoader;

import com.example.kanxuan.baidumap.Domain.AuthenticationRequest;
import com.example.kanxuan.baidumap.Http.BaseResponse;
import com.example.kanxuan.baidumap.Http.RetrofitServiceManager;
import com.example.kanxuan.baidumap.Http.UserLoginResponse;

import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by kanxuan on 2017/5/16.
 */

public class AccountLoader {

    private AccountService accountService;

    public AccountLoader() {
        accountService = RetrofitServiceManager.getInstance().create(AccountService.class);
    }

    public Observable<UserLoginResponse> Login(String username, String password) {
        return accountService.Login(username, password);
    }

    public interface AccountService {
        @POST("auth/token")
        Observable<UserLoginResponse> Login(@Query("username") String username, @Query("password") String password);
    }

}
