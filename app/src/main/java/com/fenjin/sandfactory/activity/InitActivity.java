package com.fenjin.sandfactory.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.fenjin.data.DataRepository;
import com.fenjin.sandfactory.R;
import com.fenjin.sandfactory.app.BaseApplication;

public class InitActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_init);
        DataRepository dataRepository = ((BaseApplication) getApplication()).getDataRepository();

        if (TextUtils.isEmpty(dataRepository.getToken())){
            startActivity(new Intent(this, LoginActivity.class));
        }else {
            startActivity(new Intent(this, MainActivity.class));
        }

        finish();
    }
}
