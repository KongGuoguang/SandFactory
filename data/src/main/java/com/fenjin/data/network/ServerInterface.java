package com.fenjin.data.network;

import com.fenjin.data.bean.PersonalInfo;
import com.fenjin.data.entity.BalanceQueryResult;
import com.fenjin.data.entity.ChengZhongRecordListResult;
import com.fenjin.data.entity.EditPersonalInfoResult;
import com.fenjin.data.entity.GetChartStaticResult;
import com.fenjin.data.entity.GetChengZhongStatisticsResult;
import com.fenjin.data.entity.GetStaticDetailCountResult;
import com.fenjin.data.entity.GetSysConfigResult;
import com.fenjin.data.entity.LoadCompanyNamesResult;
import com.fenjin.data.entity.LoadPersonInfoResult;
import com.fenjin.data.entity.LoadSiteNamesResult;
import com.fenjin.data.entity.LoginParam;
import com.fenjin.data.entity.LoginResult;
import com.fenjin.data.entity.ModifyPasswordParam;
import com.fenjin.data.entity.ModifyPasswordResult;
import com.fenjin.data.entity.StatisticQueryCountResult;
import com.fenjin.data.entity.StatisticQueryDetailListResult;
import com.fenjin.data.entity.StatisticQueryListResult;
import com.fenjin.data.entity.TodayCountResult;
import com.fenjin.data.entity.UploadHeadImgResult;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Author:kongguoguang
 * Date:2018-05-15
 * Time:15:49
 * Summary:
 */
public interface ServerInterface {

    @POST("public/login")
    Observable<LoginResult> login(@Body LoginParam loginParam);

    @POST("sysuser/updatePwd")
    Observable<ModifyPasswordResult> modifyPassword(@Header("Authorization") String authorization,
                                                    @Body ModifyPasswordParam modifyPasswordParam);


    @GET("record/list")
    Observable<ChengZhongRecordListResult> getChengZhongRecordList(@Header("Authorization") String authorization,
                                                                   @Query("pageNum") int pageNum,
                                                                   @Query("pageSize") int pageSize,
                                                                   @Query("searchKey") String searchKey);

    @GET("record/list")
    Observable<ChengZhongRecordListResult> getChengZhongRecordList(@Header("Authorization") String authorization,
                                                                   @QueryMap Map<String, Object> param);

    @GET("record/todayCount")
    Observable<TodayCountResult> getTodayCountResult(@Header("Authorization") String authorization);

    @FormUrlEncoded
    @POST("record/newcount")
    Observable<GetChengZhongStatisticsResult> getChengZhongStaticResult(@Header("Authorization") String authorization,
                                                                        @FieldMap Map<String, Object> param);

    @GET("record/echart/count")
    Observable<GetChartStaticResult> getChartStaticResult(@Header("Authorization") String authorization);

    @FormUrlEncoded
    @POST("record/statisticalQuery/count")
    Observable<StatisticQueryCountResult> getStatisticQueryCount(@Header("Authorization") String authorization,
                                                                 @FieldMap Map<String, Object> param);

    @FormUrlEncoded
    @POST("record/statisticalQuery/list")
    Observable<StatisticQueryListResult> getStatisticQueryList(@Header("Authorization") String authorization,
                                                               @FieldMap Map<String, Object> param);

    @FormUrlEncoded
    @POST("record/statisticalQuery/detail/count")
    Observable<GetStaticDetailCountResult> getStaticDetailCount(@Header("Authorization") String authorization,
                                                                @FieldMap Map<String, Object> param);

    @FormUrlEncoded
    @POST("record/statisticalQuery/detail/list")
    Observable<StatisticQueryDetailListResult> getStatisticQueryDetailList(@Header("Authorization") String authorization,
                                                                           @FieldMap Map<String, Object> param);

    @GET("prepayment/enterpriselist")
    Observable<BalanceQueryResult> getBalanceQueryResult(@Header("Authorization") String authorization,
                                                         @QueryMap Map<String, Object> map);

    @GET("public/sysconf/all")
    Observable<GetSysConfigResult> getSysConfig();

    @GET("sysuser/getCurrent")
    Observable<LoadPersonInfoResult> getPersonalInfo(@Header("Authorization") String authorization);

    @POST("sysuser/edit")
    Observable<EditPersonalInfoResult> editPersonalInfo(@Header("Authorization") String authorization,
                                                        @Body PersonalInfo param);

    @POST("sysuser/upload/headImg")
    Observable<UploadHeadImgResult> uploadHeadImg(@Header("Authorization") String authorization,
                                                  @Body PersonalInfo param);

    @GET("sandfactory/list")
    Observable<LoadSiteNamesResult> getSandFactoryNames(@Header("Authorization") String authorization);

    @GET("prepayment/enterpriseNamelist")
    Observable<LoadCompanyNamesResult> getCompanyNames(@Header("Authorization") String authorization);

}
