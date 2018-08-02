package com.fenjin.data;

import android.content.Context;

import com.fenjin.data.entity.ChengZhongRecordListResult;
import com.fenjin.data.entity.LoginParam;
import com.fenjin.data.entity.LoginResult;
import com.fenjin.data.network.NetworkRepository;
import com.fenjin.data.preferences.PreferencesRepository;

import io.reactivex.Observable;
import io.reactivex.Observer;

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

    public Observable<LoginResult> login(LoginParam loginParam){
        return networkRepository.login(loginParam);
    }

    public Observable<ChengZhongRecordListResult> getList(int pageNum, int pageSize){
        return networkRepository.getList(getToken(), pageNum, pageSize);
    }

    public void saveUserNameAndPassword(String userName, String password){
        preferencesRepository.saveUserName(userName);
        preferencesRepository.savePassword(password);
    }

    public String getToken(){
        return preferencesRepository.getToken();
    }

    public void saveToken(String token){
        preferencesRepository.saveToken(token);
    }
}
