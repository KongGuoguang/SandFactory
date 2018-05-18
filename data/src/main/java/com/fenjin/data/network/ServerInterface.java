package com.fenjin.data.network;

import com.fenjin.data.bean.User;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Author:kongguoguang
 * Date:2018-05-15
 * Time:15:49
 * Summary:
 */
public interface ServerInterface {

    @GET("login")
    Observable<User> login(@Query("userName") String userName,
                           @Query("password") String password);

}
