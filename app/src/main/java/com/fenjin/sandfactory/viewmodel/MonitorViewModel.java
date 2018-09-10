package com.fenjin.sandfactory.viewmodel;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.fenjin.data.entity.Channel;
import com.fenjin.data.entity.GetAllChannelResult;
import com.fenjin.sandfactory.adapter.ChannelListAdapter;
import com.fenjin.sandfactory.usecase.GetAllChannelUseCase;
import com.fenjin.sandfactory.util.ErrorCodeUtil;

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

    public MutableLiveData<Integer> errorCode = new MutableLiveData<>();

    public ChannelListAdapter adapter = new ChannelListAdapter();



    public void getAllChannel(){
        loading.postValue(true);
        getAllChannelUseCase.execute(new Observer<GetAllChannelResult>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(GetAllChannelResult getAllChannelResult) {
                loading.postValue(false);
                adapter.setChannelList(getAllChannelResult.getEasyDarwin().getBody().getChannels());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onError(Throwable e) {
                loading.postValue(false);
                errorCode.postValue(ErrorCodeUtil.getErrorCode(e));

            }

            @Override
            public void onComplete() {

            }
        });
    }


}
