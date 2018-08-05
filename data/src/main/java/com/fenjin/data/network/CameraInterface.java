package com.fenjin.data.network;

import com.fenjin.data.entity.GetAllChannelResult;
import com.fenjin.data.entity.GetChannelResult;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CameraInterface {


    @GET("getChannels")
    Observable<GetAllChannelResult> getAllChannel();

    @GET("getchannelstream")
    Observable<GetChannelResult> getChannel(@Query("channel") int channel,
                                            @Query("protocol") String protocol);

    @GET("touchchannelstream")
    Observable<GetChannelResult> touchChannel(@Query("channel") int channel,
                                              @Query("line") String line,
                                            @Query("protocol") String protocol);

}
