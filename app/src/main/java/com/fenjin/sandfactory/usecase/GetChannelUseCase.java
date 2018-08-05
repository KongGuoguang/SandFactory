package com.fenjin.sandfactory.usecase;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.fenjin.data.entity.GetChannelResult;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

public class GetChannelUseCase extends BaseUseCase<GetChannelResult> {
    public GetChannelUseCase(Context context) {
        super(context);
    }

    private int channel;

    public GetChannelUseCase get(int channel){
        this.channel = channel;
        return this;
    }

    @Override
    public Observable<GetChannelResult> buildObservable() {
        return dataRepository.getChannel(channel, "RTMP");
    }
}
