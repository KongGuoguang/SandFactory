package com.fenjin.sandfactory.usecase;

import android.content.Context;

import com.fenjin.data.entity.LoginParam;
import com.fenjin.data.entity.LoginResult;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

/**
 * Author:kongguoguang
 * Date:2018-05-17
 * Time:9:43
 * Summary:
 */
public class LoginUseCase extends BaseUseCase<LoginResult> {

    private String userName, password;

    public LoginUseCase(Context context) {
        super(context);
    }

    public LoginUseCase login(String userName, String password){
        this.userName = userName;
        this.password = password;
        return this;
    }

    @Override
    public Observable<LoginResult> buildObservable() {
        return dataRepository.login(new LoginParam(userName, password))
                .doOnNext(new Consumer<LoginResult>() {
                    @Override
                    public void accept(LoginResult loginResult) throws Exception {
                        dataRepository.saveUserNameAndPassword(userName, password);
                        dataRepository.saveToken(loginResult.getResult());
                    }
                });
    }
}
