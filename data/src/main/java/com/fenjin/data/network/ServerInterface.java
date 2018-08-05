package com.fenjin.data.network;

import com.fenjin.data.entity.ChengZhongRecordListResult;
import com.fenjin.data.entity.LoginParam;
import com.fenjin.data.entity.LoginResult;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Author:kongguoguang
 * Date:2018-05-15
 * Time:15:49
 * Summary:
 */
public interface ServerInterface {

    @POST("public/login")
    Observable<LoginResult> login(@Body LoginParam loginParam);

    @GET("record/list")
    Observable<ChengZhongRecordListResult> getChengZhongRecordList(@Header("token") String token,
                                                                   @Query("pageNum") int pageNum,
                                                                   @Query("pageSize") int pageSize,
                                                                   @Query("searchKey") String searchKey);

}
