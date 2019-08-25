package com.fenjin.sandfactory.usecase;

import android.content.Context;

import com.fenjin.data.entity.LoadSiteNamesResult;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

/**
 * Author:kongguoguang
 * Date:2019/8/24
 * Time:18:34
 * Summary:
 */
public class LoadSiteNamesUseCase extends BaseUseCase<LoadSiteNamesResult> {
    public LoadSiteNamesUseCase(Context context) {
        super(context);
    }

    @Override
    public Observable<LoadSiteNamesResult> buildObservable() {
        return dataRepository.loadSandFactoryNames()
                .doOnNext(new Consumer<LoadSiteNamesResult>() {
                    @Override
                    public void accept(LoadSiteNamesResult loadSiteNamesResult) throws Exception {
                        if (loadSiteNamesResult.getFlag() == 1) {
                            dataRepository.setSiteNames(loadSiteNamesResult.getResult());
                        }
                    }
                });
    }
}
