package com.fenjin.sandfactory.usecase;

import android.content.Context;

import com.fenjin.data.entity.GetAllChannelResult;

import io.reactivex.Observable;

public class GetAllChannelUseCase extends BaseUseCase<GetAllChannelResult> {
    public GetAllChannelUseCase(Context context) {
        super(context);
    }

    @Override
    public Observable<GetAllChannelResult> buildObservable() {
        return dataRepository.getAllChannel();
    }
}
