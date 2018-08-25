package com.fenjin.sandfactory.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import com.blankj.utilcode.util.LogUtils;
import com.fenjin.data.DataRepository;
import com.fenjin.data.entity.LoginParam;
import com.fenjin.sandfactory.app.BaseApplication;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * Author:kongguoguang
 * Date:2018-05-15
 * Time:17:10
 * Summary:
 */
public class BaseViewModel extends AndroidViewModel {


    public BaseViewModel(@NonNull Application application) {
        super(application);
        LogUtils.d("onCreate()");
        EventBus.getDefault().register(this);
    }

    public DataRepository dataRepository = ((BaseApplication)getApplication()).getDataRepository();

    @Override
    protected void onCleared() {
        super.onCleared();
        LogUtils.d("onCleared()");
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onNullEvent(LoginParam loginParam){}
}
