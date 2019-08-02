package com.fenjin.sandfactory.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Toast;

import com.fenjin.sandfactory.util.ErrorCodeUtil;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        QMUIStatusBarHelper.translucent(this);
    }

    protected void showErrorDialog(String errorMessage){
        if (TextUtils.isEmpty(errorMessage)) return;
        new QMUIDialog.MessageDialogBuilder(this)
                .setTitle("提示")
                .setMessage(errorMessage)
                .addAction("确定", new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        dialog.dismiss();
                    }
                })
                .create().show();
    }

    protected void showToast(String message){
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    protected void dealErrorCode(int errorCode) {
        switch (errorCode) {
            case ErrorCodeUtil.TOKEN_TIME_OUT:
                startActivity(new Intent(this, LoginActivity.class));
                finish();
                showToast("登录信息超时，请重新登录");
                break;
            case ErrorCodeUtil.CONNECT_ERROR:
            case ErrorCodeUtil.PROTOCOL_ERROR:
            case ErrorCodeUtil.SOCKET_ERROR:
            case ErrorCodeUtil.TIMEOUT:
                showErrorDialog("请求超时，请稍后重试");
                break;
            case ErrorCodeUtil.REPONSE_ERROR:
                showErrorDialog("服务器响应内容错误");
                break;
            case ErrorCodeUtil.SERVER_INNER_ERROR:
                showErrorDialog("服务器内部异常");
                break;
            case ErrorCodeUtil.SERVER_NOT_FOUND:
                showErrorDialog("服务器请求路径不存在");
                break;
            default:
                showErrorDialog("未知错误，错误码：" + errorCode);
        }
    }
}
