package com.fenjin.sandfactory.viewmodel;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.content.res.ColorStateList;
import android.databinding.Observable;
import android.databinding.ObservableField;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.fenjin.data.entity.Channel;
import com.fenjin.data.entity.GetAllChannelResult;
import com.fenjin.sandfactory.adapter.ChannelListAdapter;
import com.fenjin.sandfactory.usecase.GetAllChannelUseCase;
import com.fenjin.sandfactory.util.ErrorCodeUtil;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButtonDrawable;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class MonitorViewModel extends BaseViewModel {

    public QMUIRoundButtonDrawable drawable;

    public MonitorViewModel(@NonNull Application application) {
        super(application);
        drawable = new QMUIRoundButtonDrawable();
        drawable.setBgData(ColorStateList.valueOf(Color.WHITE));
        drawable.setIsRadiusAdjustBounds(true);

        searchKey.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                if (TextUtils.isEmpty(searchKey.get())) {
                    adapter.setChannelList(allChannels);
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }

    public ObservableField<String> searchKey = new ObservableField<>();

    public MutableLiveData<Boolean> loading = new MutableLiveData<>();

    private GetAllChannelUseCase getAllChannelUseCase = new GetAllChannelUseCase(getApplication());

    public MutableLiveData<Integer> errorCode = new MutableLiveData<>();

    public ChannelListAdapter adapter = new ChannelListAdapter();

    private List<Channel> allChannels;



    public void getAllChannel(){
        loading.postValue(true);

        getAllChannelUseCase.execute(new Observer<GetAllChannelResult>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(GetAllChannelResult getAllChannelResult) {
                loading.postValue(false);
                allChannels = getAllChannelResult.getEasyDarwin().getBody().getChannels();
                adapter.setChannelList(allChannels);
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

    public void searchChannel() {
        String key = searchKey.get();

        if (TextUtils.isEmpty(key)) return;

        List<Channel> channelList = new ArrayList<>();

        for (Channel channel : allChannels) {
            String channelName = channel.getName();
            if (TextUtils.isEmpty(channelName)) continue;
            if (channelName.contains(key)) {
                channelList.add(channel);
            }
        }

        adapter.setChannelList(channelList);
        adapter.notifyDataSetChanged();
    }


}
