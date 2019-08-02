package com.fenjin.sandfactory.viewmodel;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;

/**
 * Author:kongguoguang
 * Date:2019/8/2
 * Time:10:24
 * Summary:
 */
public class PersonalInfoViewModel extends BaseViewModel {

    public static final int DIALOG_MODIFY_PHONE_NUMBER = 0;

    public static final int DIALOG_MODIFY_ADDRESS = 1;

    public static final int DIALOG_MODIFY_COMPANY = 2;


    public PersonalInfoViewModel(@NonNull Application application) {
        super(application);
    }

    //头像url
    public ObservableField<String> profilePictureUrl = new ObservableField<>();

    //姓名
    public ObservableField<String> name = new ObservableField<>();

    //性别
    public ObservableField<String> sex = new ObservableField<>();

    //手机号
    public ObservableField<String> phoneNumber = new ObservableField<>();

    //地址
    public ObservableField<String> address = new ObservableField<>();

    //公司
    public ObservableField<String> company = new ObservableField<>();

    //二维码url
    public ObservableField<String> twoDimensionalCodeUrl = new ObservableField<>();

    public MutableLiveData<Integer> showDialog = new MutableLiveData<>();

    /**
     * 展示头像大图
     */
    public void showProfilePicture() {
    }

    /**
     * 显示修改手机号码弹框
     */
    public void showModifyPhoneNumberDialog() {
        showDialog.postValue(DIALOG_MODIFY_PHONE_NUMBER);
    }

    /**
     * 显示修改地址弹框
     */
    public void showModifyAddressDialog() {
        showDialog.postValue(DIALOG_MODIFY_ADDRESS);
    }

    /**
     * 显示修改公司弹框
     */
    public void showModifyCompanyDialog() {
        showDialog.postValue(DIALOG_MODIFY_COMPANY);
    }

    /**
     * 修改手机号码
     */
    public void modifyPhoneNumber(String number) {
    }

    /**
     * 修改地址
     */
    public void modifyAddress(String address) {
    }

    /**
     * 修改公司
     */
    public void modifyCompany(String company) {
    }


    public void finishActivity() {
    }
}
