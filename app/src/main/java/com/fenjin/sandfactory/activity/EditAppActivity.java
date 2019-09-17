package com.fenjin.sandfactory.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.fenjin.sandfactory.R;
import com.fenjin.sandfactory.adapter.AppAdapter;
import com.fenjin.sandfactory.adapter.GridItemDecoration;
import com.fenjin.sandfactory.viewmodel.EditAppViewModel;

public class EditAppActivity extends BaseActivity {

    private EditAppViewModel viewModel;

    private AppAdapter appAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_app);
        viewModel = ViewModelProviders.of(this).get(EditAppViewModel.class);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerView.addItemDecoration(new GridItemDecoration());
        appAdapter = new AppAdapter(getApplicationContext());
        appAdapter.setEditMode(true);
        appAdapter.setItems(viewModel.dataRepository.getPackageNames());
        appAdapter.setOnClickListener(new AppAdapter.onClickListener() {
            @Override
            public void addApp() {
                startActivityForResult(new Intent(EditAppActivity.this, SelectAppActivity.class), 0x01);
            }

            @Override
            public void deleteApp(int position) {
                appAdapter.getItems().remove(position);
                appAdapter.notifyItemRemoved(position);
                appAdapter.notifyDataSetChanged();
                viewModel.dataRepository.setPackageNames(appAdapter.getItems());
            }

            @Override
            public void startApp(int position) {

            }
        });

        recyclerView.setAdapter(appAdapter);
    }

    public void onClick(View view) {
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (data == null) return;
            String packageName = data.getStringExtra("name");
            if (TextUtils.isEmpty(packageName)) return;
            appAdapter.getItems().add(packageName);
            appAdapter.notifyDataSetChanged();
            viewModel.dataRepository.setPackageNames(appAdapter.getItems());
            showToast("添加成功");
        }
    }
}
