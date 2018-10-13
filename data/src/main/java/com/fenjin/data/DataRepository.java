package com.fenjin.data;

import android.content.Context;

import com.fenjin.data.entity.ChengZhongRecordListResult;
import com.fenjin.data.entity.GetAllChannelResult;
import com.fenjin.data.entity.GetChannelResult;
import com.fenjin.data.entity.LoginParam;
import com.fenjin.data.entity.LoginResult;
import com.fenjin.data.entity.ModifyPasswordParam;
import com.fenjin.data.entity.ModifyPasswordResult;
import com.fenjin.data.entity.TodayCountResult;
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

    public Observable<LoginResult> login(LoginParam loginParam){
        return networkRepository.login(loginParam);
    }

    public Observable<ChengZhongRecordListResult> getChengZhongRecordList(int pageNum, int pageSize, String searchKey){
        return networkRepository.getChengZhongRecordList(getToken(), pageNum, pageSize, searchKey);
    }

    public void saveUserNameAndPassword(String userName, String password){
        preferencesRepository.saveUserName(userName);
        preferencesRepository.savePassword(password);
    }

    public String getToken(){
        return preferencesRepository.getToken();
    }

    public String getUserName(){
        return preferencesRepository.getUserName();
    }

    public String getPassword(){
        return preferencesRepository.getPassword();
    }

    public void saveToken(String token){
        preferencesRepository.saveToken(token);
    }

    public Observable<GetAllChannelResult> getAllChannel(){
        return networkRepository.getAllChannel();
    }

    public Observable<GetChannelResult> getChannel(int channel, String protocol){
        return networkRepository.getChannel(channel, protocol);
    }

    public Observable<GetChannelResult> touchChannel(int channel, String line, String protocol){
        return networkRepository.touchChannel(channel, line, protocol);
    }

    public Observable<ModifyPasswordResult> modifyPassword(String token, ModifyPasswordParam modifyPasswordParam) {
        return networkRepository.modifyPassword(token, modifyPasswordParam);
    }

    public void setRememberPassword(boolean rememberPassword){
        preferencesRepository.setRememberPassword(rememberPassword);
    }

    public boolean getRememberPassword(){
        return preferencesRepository.getRememberPassword();
    }

    public Observable<TodayCountResult> getTodayCountResult(String token) {
        return networkRepository.getTodayCountResult(token);
    }
}
