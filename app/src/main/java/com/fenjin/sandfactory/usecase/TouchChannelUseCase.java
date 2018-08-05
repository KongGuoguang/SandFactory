package com.fenjin.sandfactory.usecase;

import android.content.Context;

import com.fenjin.data.entity.GetChannelResult;

import io.reactivex.Observable;

public class TouchChannelUseCase extends BaseUseCase<GetChannelResult> {
    public TouchChannelUseCase(Context context) {
        super(context);
    }

    private int channel;

    public TouchChannelUseCase touch(int channel){
        this.channel = channel;
        return this;
    }

    @Override
    public Observable<GetChannelResult> buildObservable() {
        return dataRepository.touchChannel(channel, "local", "rtmp");
    }
}
