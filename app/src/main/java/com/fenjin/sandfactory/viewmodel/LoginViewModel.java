package com.fenjin.sandfactory.viewmodel;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.databinding.Observable;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import com.blankj.utilcode.util.LogUtils;
import com.fenjin.data.entity.LoginResult;
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
        userName.set(dataRepository.getUserName());
        rememberPassword.set(dataRepository.getRememberPassword());
        if (rememberPassword.get()){
            password.set(dataRepository.getPassword());
        }
        rememberPassword.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                dataRepository.setRememberPassword(rememberPassword.get());
            }
        });
    }

    public ObservableField<String> userName = new ObservableField<>();

    public ObservableField<String> password = new ObservableField<>();

    public ObservableField<Boolean> rememberPassword = new ObservableField<>();

    public MutableLiveData<Boolean> loginSuccess = new MutableLiveData<>();

    public MutableLiveData<Boolean> loginIng = new MutableLiveData<>();

    public String loginErrorMessage = "登录失败";

    private LoginUseCase loginUseCase;

    private void loadFromSharedPreferences(){

    }

    public void login(){

//        loginSuccess.postValue(true);

        loginIng.setValue(true);
        if (loginUseCase == null){
            loginUseCase = new LoginUseCase(getApplication());
        }
        loginUseCase.login(userName.get(), password.get())
                .execute(new Observer<LoginResult>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(LoginResult loginResult) {
                        loginIng.postValue(false);
                        if (loginResult.getFlag() == 1){
                            loginSuccess.postValue(true);
                        }else {
                            loginErrorMessage = loginResult.getMessage();
                            loginSuccess.postValue(false);
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        loginIng.postValue(false);
                        LogUtils.d(e.getMessage());
                        loginErrorMessage = "登录失败,请检查网络设置";
                        loginSuccess.postValue(false);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
