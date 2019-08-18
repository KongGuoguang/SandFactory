package com.fenjin.sandfactory.usecase;

import android.content.Context;

import com.fenjin.data.bean.SysConfig;
import com.fenjin.data.entity.GetSysConfigResult;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

/**
 * Author:kongguoguang
 * Date:2019/8/5
 * Time:19:27
 * Summary:
 */
public class GetSysConfigUseCase extends BaseUseCase<GetSysConfigResult> {
    public GetSysConfigUseCase(Context context) {
        super(context);
    }

    @Override
    public Observable<GetSysConfigResult> buildObservable() {
        return dataRepository.getSysConfig()
                .doOnNext(new Consumer<GetSysConfigResult>() {
                    @Override
                    public void accept(GetSysConfigResult getSysConfigResult) throws Exception {
                        for (SysConfig config : getSysConfigResult.getResult()) {
                            if (config.getId() == 1) {
                                dataRepository.setSysName(config.getValue());
                            }

                            if (config.getId() == 2) {
                                dataRepository.setSysLogoUrl(config.getValue());
                            }

                            if (config.getId() == 3) {
                                dataRepository.setSysQrImgUrl(config.getValue());
                            }
                        }
                    }
                });
    }
}
