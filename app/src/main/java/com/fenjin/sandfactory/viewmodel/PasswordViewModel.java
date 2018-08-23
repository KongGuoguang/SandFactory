package com.fenjin.sandfactory.viewmodel;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;

/**
 * Author:kongguoguang
 * Date:2018-08-22
 * Time:19:25
 * Summary:
 */
public class PasswordViewModel extends BaseViewModel {

    public PasswordViewModel(@NonNull Application application) {
        super(application);
    }

    public ObservableField<String> oldPassword = new ObservableField<>();

    public ObservableField<String> newPassword = new ObservableField<>();

    public ObservableField<String> confirmPassword = new ObservableField<>();

    public MutableLiveData<Boolean> finishActivity = new MutableLiveData<>();

    public void submitPassword(){

        if (checkInputContent()){
            //TODO submit
        }
    }

    public void finishActivity(){
        finishActivity.postValue(true);
    }

    private boolean checkInputContent(){
        if (TextUtils.isEmpty(oldPassword.get())){
            ToastUtils.showShort("原密码不能为空！");
            return false;
        }

        if (TextUtils.isEmpty(newPassword.get())){
            ToastUtils.showShort("新密码不能为空！");
            return false;
        }

        if (TextUtils.isEmpty(confirmPassword.get())){
            ToastUtils.showShort("请确认新密码！");
            return false;
        }

        if (!newPassword.get().equals(confirmPassword.get())){
            ToastUtils.showShort("两次输入的新密码不一致！");
            return false;
        }

        return true;
    }
}
