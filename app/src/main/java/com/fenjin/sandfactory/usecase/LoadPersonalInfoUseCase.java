package com.fenjin.sandfactory.usecase;

import android.content.Context;

import com.fenjin.data.entity.LoadPersonInfoResult;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

/**
 * Author:kongguoguang
 * Date:2019/8/29
 * Time:14:51
 * Summary:
 */
public class LoadPersonalInfoUseCase extends BaseUseCase<LoadPersonInfoResult> {
    public LoadPersonalInfoUseCase(Context context) {
        super(context);
    }

    @Override
    public Observable<LoadPersonInfoResult> buildObservable() {
        return dataRepository.loadPersonalInfo()
                .doOnNext(new Consumer<LoadPersonInfoResult>() {
                    @Override
                    public void accept(LoadPersonInfoResult loadPersonInfoResult) throws Exception {
                        if (loadPersonInfoResult.getFlag() == 1) {
                            dataRepository.savePersonalInfo(loadPersonInfoResult.getResult());
                        }
                    }
                });
    }
}
