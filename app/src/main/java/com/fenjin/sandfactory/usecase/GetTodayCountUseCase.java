package com.fenjin.sandfactory.usecase;

import android.content.Context;

import com.fenjin.data.entity.TodayCountResult;

import io.reactivex.Observable;

public class GetTodayCountUseCase extends BaseUseCase<TodayCountResult> {
    public GetTodayCountUseCase(Context context) {
        super(context);
    }

    @Override
    public Observable<TodayCountResult> buildObservable() {
        return dataRepository.getTodayCountResult();
    }
}