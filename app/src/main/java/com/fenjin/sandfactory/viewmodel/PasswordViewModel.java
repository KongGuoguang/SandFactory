package com.fenjin.sandfactory.viewmodel;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.fenjin.data.entity.ModifyPasswordResult;
import com.fenjin.sandfactory.usecase.ModifyPasswordUseCase;
import com.fenjin.sandfactory.util.ErrorCodeUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

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

    public MutableLiveData<Integer> errorCode = new MutableLiveData<>();

    public MutableLiveData<String> errorMessage = new MutableLiveData<>();

    public MutableLiveData<Boolean> loading = new MutableLiveData<>();

    public ModifyPasswordUseCase modifyPasswordUseCase = new ModifyPasswordUseCase(getApplication());

    public void submitPassword(){

        if (checkInputContent()){
            modifyPassword();
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

        if (!isFormatWord(newPassword.get())){
            ToastUtils.showShort("新密码格式错误！");
            return false;
        }

        if (!isFormatWord(confirmPassword.get())){
            ToastUtils.showShort("确认密码格式错误！");
            return false;
        }

        if (!newPassword.get().equals(confirmPassword.get())){
            ToastUtils.showShort("两次输入的新密码不一致！");
            return false;
        }

        return true;
    }

    private void modifyPassword(){

        loading.postValue(true);

        modifyPasswordUseCase.modify(oldPassword.get(), newPassword.get())
                .execute(new Observer<ModifyPasswordResult>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ModifyPasswordResult modifyPasswordResult) {

                        loading.postValue(false);

                        if (modifyPasswordResult.getFlag() == 1){
                            dataRepository.saveToken("");
                            errorCode.postValue(0);
                        }else {
                            errorMessage.postValue(modifyPasswordResult.getMessage());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        loading.postValue(false);
                        errorCode.postValue(ErrorCodeUtil.getErrorCode(e));

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * 判断字符串是否符合密码规则：数字和字母组合
     * @param str
     * @return
     */
    private boolean isFormatWord(String str) {

        if (TextUtils.isEmpty(str)) return false;

        if (str.length() < 6) return false;

        Pattern pattern = Pattern.compile("^[A-Za-z0-9]+$");

        Matcher isEngWord = pattern.matcher(str);

        return isEngWord.matches();
    }
}
