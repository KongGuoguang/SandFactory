package com.fenjin.sandfactory;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;

import com.fenjin.data.DataRepository;

public class InitActivity extends AppCompatActivity {

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
