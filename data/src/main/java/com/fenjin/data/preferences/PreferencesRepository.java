package com.fenjin.data.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.fenjin.data.bean.AppInfo;
import com.fenjin.data.entity.GetPersonInfoResult;

import java.util.Arrays;
import java.util.List;

/**
 * Author:kongguoguang
 * Date:2018-05-15
 * Time:16:12
 * Summary:
 */
public class PreferencesRepository {

    private static final String PREFERENCES_FILE_NAME = "sand_factory_info";

    private static final String SYS_LOGO_URL = "sys_logo_url";

    private static final String SYS_NAME = "sys_name";

    private static final String SYS_QR_IMG_URL = "sys_qr_img_url";

    private static final String USER_NAME = "user_name";

    private static final String PASSWORD = "password";

    private static final String TOKEN = "token";

    private static final String REMEMBER_PASSWORD = "remember_password";

    private static final String SELECTED_APP_PACKAGE_NAME = "_selected_app_package_name";

    private static final String IP = "ip";

    private static final String PORT = "port";

    private static final String ID = "id";

    private static final String NAME = "name";

    private static final String PHONE = "phone";

    private static final String SEX = "sex";

    private static final String ADDRESS = "address";

    private static final String COMPANY = "company";

    private static final String HEAD_IMG = "head_img";



    private SharedPreferences sharedPreferences;

    private SharedPreferences userPreferences;

    public PreferencesRepository(Context context) {
        init(context);
    }

    private void init(Context context){
        sharedPreferences = context.getSharedPreferences(PREFERENCES_FILE_NAME, Context.MODE_PRIVATE);
        createUserPreferences(context);
    }

    public void createUserPreferences(Context context) {
        if (TextUtils.isEmpty(getUserName())) return;
        userPreferences = context.getSharedPreferences(getUserName(), Context.MODE_PRIVATE);
    }

    public String getSysLogoUrl() {
        return sharedPreferences.getString(SYS_LOGO_URL, "");
    }

    public void setSysLogoUrl(String sysLogo) {
        sharedPreferences.edit().putString(SYS_LOGO_URL, sysLogo).apply();
    }

    public String getSysName() {
        return sharedPreferences.getString(SYS_NAME, "智慧采砂");
    }

    public void setSysName(String sysName) {
        sharedPreferences.edit().putString(SYS_NAME, sysName).apply();
    }

    public String getSysQrImgUrl() {
        return sharedPreferences.getString(SYS_QR_IMG_URL, "");
    }

    public void setSysQrImgUrl(String sysQrImg) {
        sharedPreferences.edit().putString(SYS_QR_IMG_URL, sysQrImg).apply();
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

    /**
     * Bearer + 空格 + token 构成Authorization
     * 业务请求需要在header里面携带该字段
     */
    public String getAuthorization() {
        return "Bearer " + sharedPreferences.getString(TOKEN, "");
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

    public String getIp() {
        return sharedPreferences.getString(IP, "111.6.77.67");
    }

    public void setIp(String ip) {
        sharedPreferences.edit().putString(IP, ip).apply();
    }

    public String getPort() {
        return sharedPreferences.getString(PORT, "9090");
    }

    public void setPort(String port) {
        sharedPreferences.edit().putString(PORT, port).apply();
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

    public void savePersonalInfo(GetPersonInfoResult.PersonalInfo personalInfo) {
        userPreferences.edit()
                .putInt(ID, personalInfo.getId())
                .putString(NAME, personalInfo.getName())
                .putString(ADDRESS, personalInfo.getAddress())
                .putString(PHONE, personalInfo.getMobileTel())
                .putString(HEAD_IMG, personalInfo.getHeadImg())
                .putInt(SEX, personalInfo.getSex())
                .putString(COMPANY, personalInfo.getCompany())
                .apply();
    }

    public GetPersonInfoResult.PersonalInfo getPersonalInfo() {
        if (userPreferences == null) return null;
        GetPersonInfoResult.PersonalInfo personalInfo = new GetPersonInfoResult().new PersonalInfo();
        personalInfo.setId(userPreferences.getInt(ID, 0));
        personalInfo.setName(userPreferences.getString(NAME, ""));
        personalInfo.setSex(userPreferences.getInt(SEX, 0));
        personalInfo.setHeadImg(userPreferences.getString(HEAD_IMG, ""));
        personalInfo.setMobileTel(userPreferences.getString(PHONE, ""));
        personalInfo.setAddress(userPreferences.getString(ADDRESS, ""));
        personalInfo.setCompany(userPreferences.getString(COMPANY, ""));
        return personalInfo;
    }
}
