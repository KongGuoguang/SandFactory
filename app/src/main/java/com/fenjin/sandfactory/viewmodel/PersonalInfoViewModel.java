package com.fenjin.sandfactory.viewmodel;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.view.View;

import com.fenjin.data.entity.UploadHeadImgResult;
import com.fenjin.sandfactory.usecase.UploadHeadImgUseCase;
import com.fenjin.sandfactory.util.ErrorCodeUtil;

import java.io.File;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Author:kongguoguang
 * Date:2019/8/2
 * Time:10:24
 * Summary:
 */
public class PersonalInfoViewModel extends BaseViewModel {


    public PersonalInfoViewModel(@NonNull Application application) {
        super(application);
    }


    public MutableLiveData<Integer> clickedViewId = new MutableLiveData<>();

    public UploadHeadImgUseCase useCase = new UploadHeadImgUseCase(getApplication());

    public MutableLiveData<String> loadingMsg = new MutableLiveData<>();

    public MutableLiveData<Integer> errorCode = new MutableLiveData<>();

    public MutableLiveData<String> errorMsg = new MutableLiveData<>();

    @Override
    public void onClick(View view) {
        super.onClick(view);
        clickedViewId.postValue(view.getId());
    }

    public void uploadHeadImg(File file) {
        useCase.upload(file).execute(new Observer<UploadHeadImgResult>() {
            @Override
            public void onSubscribe(Disposable d) {
                loadingMsg.postValue("正在上传");
            }

            @Override
            public void onNext(UploadHeadImgResult uploadHeadImgResult) {
                loadingMsg.postValue("");
                if (uploadHeadImgResult.getFlag() == 1) {
                    showToast("上传成功");
                } else {
                    errorMsg.postValue(uploadHeadImgResult.getMessage());
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
}
