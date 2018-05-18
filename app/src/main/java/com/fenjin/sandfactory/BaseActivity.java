package com.fenjin.sandfactory;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void showErrorDialog(String errorMessage){
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
}
