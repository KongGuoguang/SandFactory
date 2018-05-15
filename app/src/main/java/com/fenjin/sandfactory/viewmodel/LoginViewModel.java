package com.fenjin.sandfactory.viewmodel;

import android.app.Application;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import com.blankj.utilcode.util.LogUtils;

/**
 * Author:kongguoguang
 * Date:2018-05-15
 * Time:11:35
 * Summary:
 */
public class LoginViewModel extends BaseViewModel {

    public LoginViewModel(@NonNull Application application) {
        super(application);
    }

    public ObservableField<String> userName = new ObservableField<>();

    public ObservableField<String> password = new ObservableField<>();

    private void loadFromSharedPreferences(){

    }

    public void login(){
        new Thread(){
            @Override
            public void run() {
                LogUtils.d(TAG,"sleep 5s");
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                LogUtils.d(TAG,"update value");
                userName.set("张三");
                password.set("123456");
            }
        }.start();
    }
}
