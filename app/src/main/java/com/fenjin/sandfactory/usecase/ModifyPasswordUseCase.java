package com.fenjin.sandfactory.usecase;

import android.content.Context;

import com.fenjin.data.entity.ModifyPasswordParam;
import com.fenjin.data.entity.ModifyPasswordResult;

import io.reactivex.Observable;

/**
 * Author:kongguoguang
 * Date:2018-08-25
 * Time:15:30
 * Summary:
 */
public class ModifyPasswordUseCase extends BaseUseCase<ModifyPasswordResult> {

    public ModifyPasswordUseCase(Context context) {
        super(context);
    }

    private String oldPwd, newPwd;

    public ModifyPasswordUseCase modify(String oldPwd, String newPwd){
        this.oldPwd = oldPwd;
        this.newPwd = newPwd;
        return this;
    }

    @Override
    public Observable<ModifyPasswordResult> buildObservable() {
        return dataRepository.modifyPassword(new ModifyPasswordParam(oldPwd, newPwd));
    }
}
