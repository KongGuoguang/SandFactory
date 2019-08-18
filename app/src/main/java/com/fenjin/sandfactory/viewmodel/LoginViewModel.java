package com.fenjin.sandfactory.viewmodel;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.databinding.Observable;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.view.View;

import com.fenjin.data.entity.GetSysConfigResult;
import com.fenjin.data.entity.LoginResult;
import com.fenjin.sandfactory.R;
import com.fenjin.sandfactory.usecase.GetSysConfigUseCase;
import com.fenjin.sandfactory.usecase.LoginUseCase;
import com.fenjin.sandfactory.util.ErrorCodeUtil;

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

    public final int CLICK_INTENT_FINISH = 1;

    public final int CLICK_INTENT_CONFIG_IP = 2;

    public MutableLiveData<Integer> clickLiveData = new MutableLiveData<>();

    public ObservableField<String> sysLogo = new ObservableField<>(dataRepository.getSysLogoUrl());

    public ObservableField<String> sysName = new ObservableField<>(dataRepository.getSysName());

    public ObservableField<String> userName = new ObservableField<>();

    public ObservableField<String> password = new ObservableField<>();

    public ObservableField<Boolean> rememberPassword = new ObservableField<>();

    public MutableLiveData<Boolean> loginSuccess = new MutableLiveData<>();

    public MutableLiveData<String> loading = new MutableLiveData<>();

    public MutableLiveData<Integer> errorCode = new MutableLiveData<>();

    public MutableLiveData<String> errorMessage = new MutableLiveData<>();

    private LoginUseCase loginUseCase;

    private GetSysConfigUseCase getSysConfigUseCase;

    public void loadSysConfig() {
        if (getSysConfigUseCase == null) {
            getSysConfigUseCase = new GetSysConfigUseCase(getApplication());
        }

        getSysConfigUseCase.execute(new Observer<GetSysConfigResult>() {
            @Override
            public void onSubscribe(Disposable d) {
                loading.setValue("正在加载配置");
            }

            @Override
            public void onNext(GetSysConfigResult getSysConfigResult) {
                loading.postValue("");
                if (getSysConfigResult.getFlag() == 1) {
                    sysLogo.set(dataRepository.getSysLogoUrl());
                    sysName.set(dataRepository.getSysName());
                } else {
                    errorMessage.postValue(getSysConfigResult.getMessage());
                }

            }

            @Override
            public void onError(Throwable e) {
                loading.postValue("");
                errorCode.postValue(ErrorCodeUtil.getErrorCode(e));
            }

            @Override
            public void onComplete() {

            }
        });
    }


    private void login() {

        if (loginUseCase == null){
            loginUseCase = new LoginUseCase(getApplication());
        }
        loginUseCase.login(userName.get(), password.get())
                .execute(new Observer<LoginResult>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        loading.setValue("正在登录");
                    }

                    @Override
                    public void onNext(LoginResult loginResult) {
                        loading.postValue("");
                        if (loginResult.getFlag() == 1){
                            loginSuccess.postValue(true);
                        }else {
                            errorMessage.postValue(loginResult.getMessage());
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        loading.postValue("");
                        errorCode.postValue(ErrorCodeUtil.getErrorCode(e));
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_login:
                login();
                break;
            case R.id.iv_back:
                clickLiveData.postValue(CLICK_INTENT_FINISH);
                break;
            case R.id.iv_setting:
                clickLiveData.postValue(CLICK_INTENT_CONFIG_IP);
                break;
        }
    }
}
