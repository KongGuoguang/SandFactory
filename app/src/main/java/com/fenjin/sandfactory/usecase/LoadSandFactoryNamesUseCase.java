package com.fenjin.sandfactory.usecase;

import android.content.Context;

import com.fenjin.data.entity.LoadSandFactoryNamesResult;

import io.reactivex.Observable;

/**
 * Author:kongguoguang
 * Date:2019/8/24
 * Time:18:34
 * Summary:
 */
public class LoadSandFactoryNamesUseCase extends BaseUseCase<LoadSandFactoryNamesResult> {
    public LoadSandFactoryNamesUseCase(Context context) {
        super(context);
    }

    @Override
    public Observable<LoadSandFactoryNamesResult> buildObservable() {
        return dataRepository.loadSandFactoryNames();
    }
}
