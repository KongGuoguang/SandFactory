package com.fenjin.sandfactory.usecase;

import android.content.Context;

import com.fenjin.data.bean.PersonalInfo;
import com.fenjin.data.entity.EditPersonalInfoResult;

import io.reactivex.Observable;

public class EditPersonalInfoUseCase extends BaseUseCase<EditPersonalInfoResult> {

    public EditPersonalInfoUseCase(Context context) {
        super(context);
    }

    private PersonalInfo personalInfo;

    public EditPersonalInfoUseCase edit(PersonalInfo personalInfo) {
        this.personalInfo = personalInfo;
        return this;
    }

    @Override
    public Observable<EditPersonalInfoResult> buildObservable() {
        return dataRepository.editPersonalInfo(personalInfo);
    }
}
