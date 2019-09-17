package com.fenjin.sandfactory.viewmodel;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.databinding.Observable;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;

import com.fenjin.data.bean.PersonalInfo;
import com.fenjin.data.entity.EditPersonalInfoResult;
import com.fenjin.sandfactory.R;
import com.fenjin.sandfactory.activity.EditPersonalInfoActivity;
import com.fenjin.sandfactory.usecase.EditPersonalInfoUseCase;
import com.fenjin.sandfactory.util.ErrorCodeUtil;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class EditPersonalInfoViewModel extends BaseViewModel {

    public ObservableField<String> title = new ObservableField<>();

    public ObservableField<Integer> editType = new ObservableField<>();

    public ObservableField<String> inputContent = new ObservableField<>();

    public EditPersonalInfoViewModel(@NonNull Application application) {
        super(application);
        editType.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                switch (editType.get()) {
                    case EditPersonalInfoActivity.EDIT_INFO_TYPE_NAME:
                        title.set("修改姓名");
                        break;
                    case EditPersonalInfoActivity.EDIT_INFO_TYPE_SEX:
                        title.set("修改性别");
                        break;
                    case EditPersonalInfoActivity.EDIT_INFO_TYPE_PHONE:
                        title.set("修改手机号");
                        break;
                    case EditPersonalInfoActivity.EDIT_INFO_TYPE_ADDRESS:
                        title.set("修改地址");
                        break;
                    case EditPersonalInfoActivity.EDIT_INFO_TYPE_COMPANY:
                        title.set("修改单位");
                        break;
                }
            }
        });
    }

    private EditPersonalInfoUseCase useCase = new EditPersonalInfoUseCase(getApplication());

    public MutableLiveData<Integer> clickedViewId = new MutableLiveData<>();

    public MutableLiveData<String> loadingMsg = new MutableLiveData<>();

    public MutableLiveData<Integer> errorCode = new MutableLiveData<>();

    public MutableLiveData<String> errorMsg = new MutableLiveData<>();

    public void editPersonalInfo() {
        if (TextUtils.isEmpty(inputContent.get())) {
            showToast("输入内容不能为空");
            return;
        }

        PersonalInfo personalInfo = dataRepository.getPersonalInfo();
        if (editType.get() == EditPersonalInfoActivity.EDIT_INFO_TYPE_NAME) {
            personalInfo.setName(inputContent.get());
        } else if (editType.get() == EditPersonalInfoActivity.EDIT_INFO_TYPE_SEX) {
            if ("男".endsWith(inputContent.get())) {
                personalInfo.setSex(1);
            } else if ("女".endsWith(inputContent.get())) {
                personalInfo.setSex(2);
            } else {
                showToast("性别只能输入男或女");
            }
        } else if (editType.get() == EditPersonalInfoActivity.EDIT_INFO_TYPE_PHONE) {
            personalInfo.setMobileTel(inputContent.get());
        } else if (editType.get() == EditPersonalInfoActivity.EDIT_INFO_TYPE_ADDRESS) {
            personalInfo.setAddress(inputContent.get());
        } else if (editType.get() == EditPersonalInfoActivity.EDIT_INFO_TYPE_COMPANY) {
            personalInfo.setCompany(inputContent.get());
        }

        useCase.edit(personalInfo).execute(new Observer<EditPersonalInfoResult>() {
            @Override
            public void onSubscribe(Disposable d) {
                loadingMsg.postValue("正在保存");
            }

            @Override
            public void onNext(EditPersonalInfoResult editPersonalInfoResult) {
                loadingMsg.postValue("");
                if (editPersonalInfoResult.getFlag() == 1) {
                    showToast("保存成功");
                    clickedViewId.postValue(R.id.tv_cancel);
                } else {
                    errorMsg.postValue(editPersonalInfoResult.getMessage());
                }
            }

            @Override
            public void onError(Throwable e) {
                loadingMsg.postValue("");
                errorCode.postValue(ErrorCodeUtil.getErrorCode(e));
            }

            @Override
            public void onComplete() {

            }
        });
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        clickedViewId.postValue(view.getId());
    }
}
