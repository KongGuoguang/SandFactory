package com.fenjin.sandfactory.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import com.blankj.utilcode.util.LogUtils;
import com.fenjin.data.DataRepository;
import com.fenjin.data.bean.User;
import com.fenjin.sandfactory.BaseApplication;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * Author:kongguoguang
 * Date:2018-05-15
 * Time:17:10
 * Summary:
 */
public class BaseViewModel extends AndroidViewModel {

    protected static String TAG;

    public BaseViewModel(@NonNull Application application) {
        super(application);
        TAG = getClass().getSimpleName();
        LogUtils.d(TAG, "onCreate()");
        EventBus.getDefault().register(this);
    }

    protected DataRepository dataRepository = ((BaseApplication)getApplication()).getDataRepository();

    @Override
    protected void onCleared() {
        super.onCleared();
        LogUtils.d(TAG, "onCleared()");
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onNullEvent(User user){}
}
