package com.fenjin.sandfactory.usecase;

import android.content.Context;

import com.fenjin.data.entity.LoadSandFactoryNamesResult;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

public class SandFactoryNamesUseCase extends BaseUseCase<LoadSandFactoryNamesResult> {
    public SandFactoryNamesUseCase(Context context) {
        super(context);
    }

    @Override
    public Observable<LoadSandFactoryNamesResult> buildObservable() {
        return dataRepository.loadSandFactoryNames()
                .doOnNext(new Consumer<LoadSandFactoryNamesResult>() {
                    @Override
                    public void accept(LoadSandFactoryNamesResult loadSandFactoryNamesResult) throws Exception {
                        if (loadSandFactoryNamesResult.getFlag() == 1) {
                            dataRepository.setSandFactoryNames(loadSandFactoryNamesResult.getResult());
                        }
                    }
                });
    }
}
