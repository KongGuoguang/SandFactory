package com.fenjin.data.network;

import com.fenjin.data.entity.ChengZhongRecordListResult;
import com.fenjin.data.entity.LoginParam;
import com.fenjin.data.entity.LoginResult;
import com.fenjin.data.entity.ModifyPasswordParam;
import com.fenjin.data.entity.ModifyPasswordResult;
import com.fenjin.data.entity.TodayCountResult;

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

    @POST("user/updatePwd")
    Observable<ModifyPasswordResult> modifyPassword(@Header("token") String token,
                                                    @Body ModifyPasswordParam modifyPasswordParam);

    @GET("user/updatePwd")
    Observable<ModifyPasswordResult> modifyPassword(@Header("token") String token,
                                                    @Query("oldPwd") String oldPwd,
                                                    @Query("newPwd") String newPwd);

    @GET("record/list")
    Observable<ChengZhongRecordListResult> getChengZhongRecordList(@Header("token") String token,
                                                                   @Query("pageNum") int pageNum,
                                                                   @Query("pageSize") int pageSize,
                                                                   @Query("searchKey") String searchKey);

    @GET("record/todayCount")
    Observable<TodayCountResult> getTodayCountResult(@Header("token") String token);

}
