package com.fenjin.sandfactory.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;

import com.fenjin.data.bean.AppInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * AppInfo工具类
 */

public class AppInfoUtil {

    public static PackageInfo getPackageInfo(Context context, String packageName) {
        if (context == null || TextUtils.isEmpty(packageName)) {
            return null;
        }

        PackageInfo packageInfo = null;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(packageName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return packageInfo;
    }

    public static AppInfo getAppInfo(Context context, String packageName) {
        PackageManager packageManager = context.getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(packageName, 0);
            AppInfo appInfo = new AppInfo();
            appInfo.setAppName(packageInfo.applicationInfo.loadLabel(packageManager).toString());
            appInfo.setAppIcon(packageInfo.applicationInfo.loadIcon(packageManager));
            appInfo.setPackageName(packageName);
            return appInfo;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<AppInfo> getAppInfoList(Context context) {

        List<AppInfo> appInfoList = new ArrayList<>();

        PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
        for (PackageInfo packageInfo : packageInfos) {
            //排除系统应用
            if ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0) continue;

            AppInfo appInfo = new AppInfo();
            appInfo.setAppName(packageInfo.applicationInfo.loadLabel(packageManager).toString());
            appInfo.setAppIcon(packageInfo.applicationInfo.loadIcon(packageManager));
            appInfo.setPackageName(packageInfo.packageName);

            appInfoList.add(appInfo);
        }

        return appInfoList;
    }

}
