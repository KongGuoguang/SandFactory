package com.fenjin.common;

import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;

/**
 * Author:kongguoguang
 * Date:2018-05-17
 * Time:15:45
 * Summary:
 */
public class ToolUtil {

    private ToolUtil(){}

    private static ToolUtil instance;

    public static ToolUtil getInstance(){
        if (instance == null){
            synchronized (ToolUtil.class){
                if (instance == null){
                    instance = new ToolUtil();
                }
            }
        }
        return instance;
    }

    /**
     * 设置控件不可用,颜色变灰
     * @param view
     * @param flag
     */
    public void setViewEnable(View view, boolean flag){
        if (view == null){
            return;
        }
        if (flag) {
            view.setEnabled(true);
            view.setAlpha(1f);
            return;
        }
        view.setEnabled(false);
        view.setAlpha(0.5f);
    }

    /**
     * 检测当的网络（WLAN、3G/2G）状态
     * @param context Context
     * @return true 表示网络可用
     */
    public boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (info != null && info.isAvailable()) {
                // 当前网络是连接的
                return info.getState() == NetworkInfo.State.CONNECTED;
            }
        }
        return false;
    }

    public String getVersion(Context mContext){
        String versionName = "";
        try {
            versionName = mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0).versionName;
        }catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionName;
    }

}
