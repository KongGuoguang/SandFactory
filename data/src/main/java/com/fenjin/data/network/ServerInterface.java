package com.fenjin.data.network;

import com.fenjin.data.bean.PersonalInfo;
import com.fenjin.data.entity.ChengZhongRecordListResult;
import com.fenjin.data.entity.ChengZhongStatisticsParam;
import com.fenjin.data.entity.EditPersonalInfoResult;
import com.fenjin.data.entity.GetChartStaticResult;
import com.fenjin.data.entity.GetChengZhongStatisticsResult;
import com.fenjin.data.entity.GetPersonInfoResult;
import com.fenjin.data.entity.GetStaticCountParam;
import com.fenjin.data.entity.GetStaticCountResult;
import com.fenjin.data.entity.GetStaticDetailCountParam;
import com.fenjin.data.entity.GetStaticDetailCountResult;
import com.fenjin.data.entity.GetStaticDetailListParam;
import com.fenjin.data.entity.GetStaticDetailListResult;
import com.fenjin.data.entity.GetStaticListParam;
import com.fenjin.data.entity.GetStaticListResult;
import com.fenjin.data.entity.GetSysConfigResult;
import com.fenjin.data.entity.LoadCompanyNamesResult;
import com.fenjin.data.entity.LoadSandFactoryNamesResult;
import com.fenjin.data.entity.LoginParam;
import com.fenjin.data.entity.LoginResult;
import com.fenjin.data.entity.ModifyPasswordParam;
import com.fenjin.data.entity.ModifyPasswordResult;
import com.fenjin.data.entity.TodayCountResult;
import com.fenjin.data.entity.UploadHeadImgResult;

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

    @POST("sysuser/updatePwd")
    Observable<ModifyPasswordResult> modifyPassword(@Header("Authorization") String authorization,
                                                    @Body ModifyPasswordParam modifyPasswordParam);


    @GET("record/list")
    Observable<ChengZhongRecordListResult> getChengZhongRecordList(@Header("Authorization") String authorization,
                                                                   @Query("pageNum") int pageNum,
                                                                   @Query("pageSize") int pageSize,
                                                                   @Query("searchKey") String searchKey);

    @GET("record/todayCount")
    Observable<TodayCountResult> getTodayCountResult(@Header("Authorization") String authorization);

    @POST("record/newcount")
    Observable<GetChengZhongStatisticsResult> getChengZhongStaticResult(@Header("Authorization") String authorization,
                                                                        @Body ChengZhongStatisticsParam chengZhongStatisticsParam);

    @GET("record/echart/count")
    Observable<GetChartStaticResult> getChartStaticResult(@Header("Authorization") String authorization);

    @POST("record/statisticalQuery/count")
    Observable<GetStaticCountResult> getStaticCount(@Header("Authorization") String authorization,
                                                    @Body GetStaticCountParam param);

    @POST("record/statisticalQuery/list")
    Observable<GetStaticListResult> getStaticList(@Header("Authorization") String authorization,
                                                  @Body GetStaticListParam param);

    @POST("record/statisticalQuery/detail/count")
    Observable<GetStaticDetailCountResult> getStaticDetailCount(@Header("Authorization") String authorization,
                                                                @Body GetStaticDetailCountParam param);

    @POST("record/statisticalQuery/detail/list")
    Observable<GetStaticDetailListResult> getStaticDetailList(@Header("Authorization") String authorization,
                                                              @Body GetStaticDetailListParam param);

    @POST("sysconf/all")
    Observable<GetSysConfigResult> getSysConfig(@Header("Authorization") String authorization);

    @POST("sysuser/getCurrent")
    Observable<GetPersonInfoResult> getPersonalInfo(@Header("Authorization") String authorization);

    @POST("sysuser/edit")
    Observable<EditPersonalInfoResult> editPersonalInfo(@Header("Authorization") String authorization,
                                                        @Body PersonalInfo param);

    @POST("sysuser/upload/headImg")
    Observable<UploadHeadImgResult> uploadHeadImg(@Header("Authorization") String authorization,
                                                  @Body PersonalInfo param);

    @GET("sandfactory/list")
    Observable<LoadSandFactoryNamesResult> getSandFactoryNames(@Header("Authorization") String authorization);

    @GET("prepayment/enterpriseNamelist")
    Observable<LoadCompanyNamesResult> getCompanyNames(@Header("Authorization") String authorization);

}
