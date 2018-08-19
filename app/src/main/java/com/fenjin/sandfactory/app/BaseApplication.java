package com.fenjin.sandfactory.app;

import android.app.Application;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.Utils;
import com.fenjin.data.DataRepository;

/**
 * Author:kongguoguang
 * Date:2018-05-15
 * Time:15:38
 * Summary:
 */
public class BaseApplication extends Application {

    private DataRepository dataRepository;

    @Override
    public void onCreate() {
        super.onCreate();
        dataRepository = DataRepository.getInstance(this);
        initLUtils();
    }

    public DataRepository getDataRepository(){
        return dataRepository;
    }

    private void initLUtils(){
        Utils.init(this);
        LogUtils.getConfig().setGlobalTag("KGG");
    }
}
