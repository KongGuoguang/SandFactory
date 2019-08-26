package com.fenjin.sandfactory.usecase;

import android.content.Context;

import com.fenjin.data.entity.StatisticQueryDetailListResult;

import java.util.Map;

import io.reactivex.Observable;

public class StatisticQueryDetailListUseCase extends BaseUseCase<StatisticQueryDetailListResult> {
    public StatisticQueryDetailListUseCase(Context context) {
        super(context);
    }

    private Map<String, Object> param;

    public StatisticQueryDetailListUseCase get(Map<String, Object> param) {
        this.param = param;
        return this;
    }

    @Override
    public Observable<StatisticQueryDetailListResult> buildObservable() {
        return dataRepository.getStatisticQueryDetailList(param);
    }
}
