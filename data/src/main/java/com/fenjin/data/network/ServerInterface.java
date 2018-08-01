package com.fenjin.data.network;

import com.fenjin.data.bean.User;
import com.fenjin.data.entity.LoginResult;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Author:kongguoguang
 * Date:2018-05-15
 * Time:15:49
 * Summary:
 */
public interface ServerInterface {

    @POST("login")
    Observable<LoginResult> login(@Body User user);

}
