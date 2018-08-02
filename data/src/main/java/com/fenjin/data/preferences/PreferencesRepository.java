package com.fenjin.data.preferences;

import android.content.Context;
import android.content.SharedPreferences;

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
}
