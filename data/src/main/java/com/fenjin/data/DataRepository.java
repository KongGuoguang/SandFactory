package com.fenjin.data;

import android.content.Context;

import com.fenjin.data.bean.User;
import com.fenjin.data.network.NetworkRepository;
import com.fenjin.data.preferences.PreferencesRepository;

import io.reactivex.Observable;

/**
 * Author:kongguoguang
 * Date:2018-05-15
 * Time:15:32
 * Summary:
 */
public class DataRepository {

    private static DataRepository instance;

    private NetworkRepository networkRepository;

    private PreferencesRepository preferencesRepository;

    /**
     * 单例模式
     * @param context
     * @return
     */
    public static DataRepository getInstance(Context context) {
        if (instance == null){
            synchronized (DataRepository.class){
                if (instance == null){
                    instance = new DataRepository();
                    instance.init(context);
                }
            }
        }

        return instance;
    }

    /**
     * 初始化
     * @param context
     */
    private void init(Context context){
        networkRepository = NetworkRepository.getInstance();
        preferencesRepository = PreferencesRepository.getInstance(context);
    }

    public Observable<User> login(String userName, String password){
        return networkRepository.login(userName, password);
    }

    public void saveUserNameAndPassword(String userName, String password){
        preferencesRepository.saveUserName(userName);
        preferencesRepository.savePassword(password);
    }
}
