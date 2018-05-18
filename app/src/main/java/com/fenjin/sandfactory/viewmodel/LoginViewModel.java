package com.fenjin.sandfactory.viewmodel;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import com.blankj.utilcode.util.LogUtils;
import com.fenjin.data.bean.User;
import com.fenjin.sandfactory.usecase.LoginUseCase;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

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

    public MutableLiveData<Boolean> loginSuccess = new MutableLiveData<>();

    public MutableLiveData<Boolean> loginIng = new MutableLiveData<>();

    public String loginErrorMessage = "登录失败";

    private LoginUseCase loginUseCase;

    private void loadFromSharedPreferences(){

    }

    public void login(){
        loginIng.setValue(true);
        if (loginUseCase == null){
            loginUseCase = new LoginUseCase(getApplication());
        }
        loginUseCase.login(userName.get(), password.get())
                .execute(new Observer<User>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(User user) {
                        loginIng.postValue(false);
                        loginSuccess.postValue(true);
                    }

                    @Override
                    public void onError(Throwable e) {
                        loginIng.postValue(false);
                        LogUtils.d(e.getMessage());
                        loginSuccess.postValue(false);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
