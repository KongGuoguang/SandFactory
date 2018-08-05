package com.fenjin.sandfactory.viewmodel;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;


public class MeViewModel extends BaseViewModel {
    public MeViewModel(@NonNull Application application) {
        super(application);
        userName.set(dataRepository.getUserName());
    }

    public ObservableField<String> userName = new ObservableField<>();

   public MutableLiveData<Boolean> logoutDialog = new MutableLiveData<>();

   public void showLogoutDialog(){
       logoutDialog.postValue(true);
   }

    public void logout(){
        dataRepository.saveToken("");

    }
}
