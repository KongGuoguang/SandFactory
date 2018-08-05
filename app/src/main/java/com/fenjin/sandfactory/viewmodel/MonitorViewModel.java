package com.fenjin.sandfactory.viewmodel;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.blankj.utilcode.util.LogUtils;
import com.fenjin.data.entity.Channel;
import com.fenjin.data.entity.GetAllChannelResult;
import com.fenjin.data.entity.GetChannelResult;
import com.fenjin.sandfactory.usecase.GetAllChannelUseCase;
import com.fenjin.sandfactory.usecase.GetChannelUseCase;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class MonitorViewModel extends BaseViewModel {
    public MonitorViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<List<Channel>> channelListLive = new MutableLiveData<>();

    public MutableLiveData<Boolean> loading = new MutableLiveData<>();

    private GetAllChannelUseCase getAllChannelUseCase = new GetAllChannelUseCase(getApplication());



    public void getAllChannel(){
        loading.postValue(true);
        getAllChannelUseCase.execute(new Observer<GetAllChannelResult>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(GetAllChannelResult getAllChannelResult) {
                loading.postValue(false);
                channelListLive.postValue(getAllChannelResult.getEasyDarwin().getBody().getChannels());
            }

            @Override
            public void onError(Throwable e) {
                loading.postValue(false);

            }

            @Override
            public void onComplete() {

            }
        });
    }


}
