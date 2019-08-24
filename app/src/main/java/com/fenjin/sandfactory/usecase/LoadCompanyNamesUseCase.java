package com.fenjin.sandfactory.usecase;

import android.content.Context;

import com.fenjin.data.entity.LoadCompanyNamesResult;

import io.reactivex.Observable;

/**
 * Author:kongguoguang
 * Date:2019/8/24
 * Time:18:39
 * Summary:
 */
public class LoadCompanyNamesUseCase extends BaseUseCase<LoadCompanyNamesResult> {
    public LoadCompanyNamesUseCase(Context context) {
        super(context);
    }

    @Override
    public Observable<LoadCompanyNamesResult> buildObservable() {
        return null;
    }
}
