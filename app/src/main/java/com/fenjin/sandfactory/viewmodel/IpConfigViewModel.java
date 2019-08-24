package com.fenjin.sandfactory.viewmodel;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;

import com.fenjin.sandfactory.R;

/**
 * Author:kongguoguang
 * Date:2019/8/24
 * Time:14:19
 * Summary:
 */
public class IpConfigViewModel extends BaseViewModel {
    public IpConfigViewModel(@NonNull Application application) {
        super(application);
    }

    public ObservableField<String> ip = new ObservableField<>();
    public ObservableField<String> port = new ObservableField<>();

    public MutableLiveData<Boolean> finishActivity = new MutableLiveData<>();

    public void onClick(View view) {
        if (view.getId() == R.id.tv_cancel) {
            finishActivity.postValue(true);
        } else if (view.getId() == R.id.tv_save) {
            saveIpAndPort();
        }
    }

    private void saveIpAndPort() {
        if (TextUtils.isEmpty(ip.get())) {
            showToast("服务器地址不能为空");
            return;
        }

        if (TextUtils.isEmpty(port.get())) {
            showToast("服务器端口不能为空");
            return;
        }

        try {
            int portValue = Integer.parseInt(port.get());
            if (portValue > 65535) {
                showToast("服务器端口不合法");
                return;
            }
        } catch (Exception e) {
            showToast("服务器端口不合法");
            return;
        }

        dataRepository.setIpAndPort(ip.get(), port.get());
        finishActivity.postValue(true);
        showToast("保存成功！");
    }
}
