package com.fenjin.data.preferences;

import android.content.Context;
import android.content.SharedPreferences;

import com.fenjin.data.bean.AppInfo;

import java.util.Arrays;
import java.util.List;

/**
 * Author:kongguoguang
 * Date:2018-05-15
 * Time:16:12
 * Summary:
 */
public class PreferencesRepository {

    private static PreferencesRepository instance;

    private static final String PREFERENCES_FILE_NAME = "sand_factory_info";

    private static final String USER_NAME = "user_name";

    private static final String PASSWORD = "password";

    private static final String TOKEN = "token";

    private static final String REMEMBER_PASSWORD = "remember_password";

    private static final String SELECTED_APP_PACKAGE_NAME = "_selected_app_package_name";

    private SharedPreferences sharedPreferences;

    public static PreferencesRepository getInstance(Context context){
        if (instance == null){
            synchronized (PreferencesRepository.class){
                if (instance == null){
                    instance = new PreferencesRepository();
                    instance.init(context);
                }
            }
        }
        return instance;
    }

    private void init(Context context){
        sharedPreferences = context.getSharedPreferences(PREFERENCES_FILE_NAME, Context.MODE_PRIVATE);
    }

    public String getUserName() {
        return sharedPreferences.getString(USER_NAME, "");
    }

    public void saveUserName(String userName){
        sharedPreferences.edit().putString(USER_NAME, userName).apply();
    }

    public String getPassword() {
        return sharedPreferences.getString(PASSWORD, "");
    }

    public void savePassword(String password){
        sharedPreferences.edit().putString(PASSWORD, password).apply();
    }

    public String getToken(){
        return sharedPreferences.getString(TOKEN, "");
    }

    public void saveToken(String token){
        sharedPreferences.edit().putString(TOKEN, token).apply();
    }


    public void setRememberPassword(boolean rememberPassword){
        sharedPreferences.edit().putBoolean(REMEMBER_PASSWORD, rememberPassword).apply();
    }

    public boolean getRememberPassword(){
        return sharedPreferences.getBoolean(REMEMBER_PASSWORD, false);
    }

    public List<String> getSelectedAppPackageNameList() {
        String userName = sharedPreferences.getString(USER_NAME, "");
        String selectedAppPackageName = sharedPreferences.getString(userName + SELECTED_APP_PACKAGE_NAME, "");
        String[] tempArray = selectedAppPackageName.split(",");
        return Arrays.asList(tempArray);
    }

    public void setSelectedAppPackageName(List<AppInfo> appInfoList) {
        StringBuilder stringBuilder = new StringBuilder();
        for (AppInfo appInfo : appInfoList) {
            stringBuilder.append(appInfo.getPackageName()).append(",");
        }
        String userName = sharedPreferences.getString(USER_NAME, "");
        sharedPreferences.edit().putString(userName + SELECTED_APP_PACKAGE_NAME, stringBuilder.toString()).apply();
    }
}
