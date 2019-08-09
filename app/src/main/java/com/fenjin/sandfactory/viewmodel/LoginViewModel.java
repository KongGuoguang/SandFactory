package com.fenjin.sandfactory.viewmodel;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.databinding.Observable;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import com.fenjin.data.entity.GetSysConfigResult;
import com.fenjin.data.entity.LoginResult;
import com.fenjin.sandfactory.usecase.GetSysConfigUseCase;
import com.fenjin.sandfactory.usecase.LoginUseCase;
import com.fenjin.sandfactory.util.ErrorCodeUtil;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

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

    public ObservableField<String> sysLogo = new ObservableField<>(dataRepository.getSysLogoUrl());

    public ObservableField<String> sysName = new ObservableField<>(dataRepository.getSysName());

    public ObservableField<String> userName = new ObservableField<>();

    public ObservableField<String> password = new ObservableField<>();

    public ObservableField<Boolean> rememberPassword = new ObservableField<>();

    public MutableLiveData<Boolean> loginSuccess = new MutableLiveData<>();

    public MutableLiveData<Boolean> loginIng = new MutableLiveData<>();

    public MutableLiveData<Integer> errorCode = new MutableLiveData<>();

    public MutableLiveData<String> errorMessage = new MutableLiveData<>();

    private LoginUseCase loginUseCase;

    private GetSysConfigUseCase getSysConfigUseCase;

    public void getSysConfig() {
        if (getSysConfigUseCase == null) {
            getSysConfigUseCase = new GetSysConfigUseCase(getApplication());
        }

        getSysConfigUseCase.execute(new Consumer<GetSysConfigResult>() {
            @Override
            public void accept(GetSysConfigResult getSysConfigResult) throws Exception {
                sysLogo.set(dataRepository.getSysLogoUrl());
                sysName.set(dataRepository.getSysName());
            }
        });
    }


    public void login(){

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
                            errorMessage.postValue(loginResult.getMessage());
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        loginIng.postValue(false);
                        errorCode.postValue(ErrorCodeUtil.getErrorCode(e));
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
