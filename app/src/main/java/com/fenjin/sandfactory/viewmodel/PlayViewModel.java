package com.fenjin.sandfactory.viewmodel;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.blankj.utilcode.util.LogUtils;
import com.fenjin.data.entity.GetChannelResult;
import com.fenjin.sandfactory.usecase.GetChannelUseCase;
import com.fenjin.sandfactory.usecase.TouchChannelUseCase;

import java.util.Timer;
import java.util.TimerTask;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class PlayViewModel extends BaseViewModel {
    public PlayViewModel(@NonNull Application application) {
        super(application);
    }

    private GetChannelUseCase getChannelUseCase = new GetChannelUseCase(getApplication());

    private TouchChannelUseCase touchChannelUseCase = new TouchChannelUseCase(getApplication());

    public ObservableField<String> channelName = new ObservableField<>();

    public ObservableField<Boolean> playFullScreen = new ObservableField<>();

    public ObservableField<Boolean> playSuccess = new ObservableField<>();

    public MutableLiveData<Boolean> loading = new MutableLiveData<>();

    public MutableLiveData<String> channelUrl = new MutableLiveData<>();

    public MutableLiveData<Boolean> finishActivity = new MutableLiveData<>();

    private Timer timer;

    private TimerTask timerTask;

    public void getChannel(int channel){

        if (channel == -1) return;

        loading.postValue(true);
        getChannelUseCase.get(channel).execute(new Observer<GetChannelResult>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(GetChannelResult getChannelResult) {
                loading.postValue(false);
                String url = getChannelResult.getEasyDarwin().getBody().getURL();

                if (!TextUtils.isEmpty(url) && url.contains("{host}")){
                    url = url.replace("{host}", "112.35.23.101");
                }

                channelUrl.postValue(url);
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

    public void touchChannel(final int channel){

        if (timer != null){
            timer.cancel();
        }

        if (timerTask != null){
            timerTask.cancel();
        }

        timer = new Timer();

        timerTask = new TimerTask() {
            @Override
            public void run() {
                touchChannelUseCase.touch(channel).execute();
            }
        };

        timer.schedule(timerTask, 30000, 30000);
    }

    public void playFullScreen(boolean fullScreen){

        LogUtils.d("PlayViewModel", "playFullScreen(), fullScreen = " + fullScreen);

        playFullScreen.set(fullScreen);
    }

    public void finishActivity(){
        finishActivity.postValue(true);
    }



    @Override
    protected void onCleared() {
        super.onCleared();
        if (timer != null){
            timer.cancel();
        }

        if (timerTask != null){
            timerTask.cancel();
        }
    }
}
