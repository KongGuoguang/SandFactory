package com.fenjin.sandfactory.fragment;


import android.content.Intent;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.widget.Toast;

import com.fenjin.sandfactory.activity.LoginActivity;
import com.fenjin.sandfactory.util.ErrorCodeUtil;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;

/**
 * A simple {@link Fragment} subclass.
 */
public class BaseFragment extends Fragment {


    public BaseFragment() {
        // Required empty public constructor
    }


    protected void showErrorDialog(String errorMessage){

        if (TextUtils.isEmpty(errorMessage)) return;

        new QMUIDialog.MessageDialogBuilder(getActivity())
                .setTitle("提示")
                .setMessage(errorMessage)
                .addAction("知道了", new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        dialog.dismiss();
                    }
                })
                .create().show();
    }

    protected void showToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
    }

    protected void dealErrorCode(int errorCode) {
        switch (errorCode) {
            case ErrorCodeUtil.TOKEN_TIME_OUT:
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                getActivity().finish();
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
