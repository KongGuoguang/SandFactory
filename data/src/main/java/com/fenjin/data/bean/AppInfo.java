package com.fenjin.data.bean;

import android.graphics.drawable.Drawable;

/**
 * Author:kongguoguang
 * Date:2019/8/1
 * Time:14:54
 * Summary:
 */
public class AppInfo {

    private String appName;

    private Drawable appIcon;

    private String packageName;

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public Drawable getAppIcon() {
        return appIcon;
    }

    public void setAppIcon(Drawable appIcon) {
        this.appIcon = appIcon;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }
}
