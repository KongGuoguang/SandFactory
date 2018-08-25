package com.fenjin.sandfactory.viewmodel;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

/**
 * Author:kongguoguang
 * Date:2018-08-25
 * Time:11:28
 * Summary:
 */
public class DetailViewModel extends BaseViewModel {
    public DetailViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<Boolean> finishActivity = new MutableLiveData<>();

    public void finishActivity(){
        finishActivity.postValue(true);
    }
}
