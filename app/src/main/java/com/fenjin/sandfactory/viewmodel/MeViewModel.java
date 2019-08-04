package com.fenjin.sandfactory.viewmodel;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.view.View;

import com.fenjin.sandfactory.R;


public class MeViewModel extends BaseViewModel {
    public MeViewModel(@NonNull Application application) {
        super(application);
        userName.set(dataRepository.getUserName());
    }

    public ObservableField<String> userName = new ObservableField<>();

    public MutableLiveData<Boolean> showLogoutDialog = new MutableLiveData<>();

    public MutableLiveData<Boolean> startAboutActivity = new MutableLiveData<>();

    public MutableLiveData<Boolean> startPasswordActivity = new MutableLiveData<>();

    public MutableLiveData<Boolean> startPersonalInfoActivity = new MutableLiveData<>();

    public void logout(){
        dataRepository.saveToken("");
    }


    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.layout_personal_dta:
                startPersonalInfoActivity.postValue(true);
                break;
            case R.id.layout_modify_password:
                startPasswordActivity.postValue(true);
                break;
            case R.id.layout_about:
                startAboutActivity.postValue(true);
                break;
            case R.id.bt_logout:
                showLogoutDialog.postValue(true);
                break;
        }

    }
}
