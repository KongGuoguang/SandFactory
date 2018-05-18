package com.fenjin.sandfactory.usecase;

import android.content.Context;

import com.fenjin.data.bean.User;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

/**
 * Author:kongguoguang
 * Date:2018-05-17
 * Time:9:43
 * Summary:
 */
public class LoginUseCase extends BaseUseCase<User> {

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
    public Observable<User> buildObservable() {
        return dataRepository.login(userName, password)
                .doOnNext(new Consumer<User>() {
                    @Override
                    public void accept(User user) throws Exception {
                        dataRepository.saveUserNameAndPassword(userName, password);
                    }
                });
    }
}
