package com.fenjin.sandfactory.viewmodel;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;

public class AboutViewModel extends BaseViewModel {
    public AboutViewModel(@NonNull Application application) {
        super(application);
        versionName.set(getVersionName(getApplication()));
    }

    public ObservableField<String> versionName = new ObservableField<>();

    public ObservableField<String> updateResult = new ObservableField<>();

    public MutableLiveData<Boolean> finishActivity = new MutableLiveData<>();

    public void finishActivity(){
        finishActivity.postValue(true);
    }


    /**
     * 获取软件版本号名称
     */
    private String getVersionName(Context ctx) {
        String localVersion = "";
        try {
            PackageInfo packageInfo = ctx.getApplicationContext()
                    .getPackageManager()
                    .getPackageInfo(ctx.getPackageName(), 0);
            localVersion = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "V" + localVersion;
    }


}
