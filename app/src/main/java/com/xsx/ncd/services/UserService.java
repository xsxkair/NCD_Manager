package com.xsx.ncd.services;

import com.xsx.ncd.entity.User;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface UserService {

    @POST("login")
    Call<User> loginService(@Query("account") String account, @Query("password") String password);

    @POST("login")
    Observable<User> loginService2(@Query("account") String account, @Query("password") String password);
}
