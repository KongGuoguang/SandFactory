package com.fenjin.sandfactory.usecase;

import android.content.Context;

import com.fenjin.data.entity.GetChartStaticResult;

import io.reactivex.Observable;

/**
 * Author:kongguoguang
 * Date:2019/8/6
 * Time:9:17
 * Summary:
 */
public class ChartStaticUseCase extends BaseUseCase<GetChartStaticResult> {
    public ChartStaticUseCase(Context context) {
        super(context);
    }

    @Override
    public Observable<GetChartStaticResult> buildObservable() {
        return dataRepository.getChartStatic();
    }
}
