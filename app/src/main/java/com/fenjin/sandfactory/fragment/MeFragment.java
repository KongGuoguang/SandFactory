package com.fenjin.sandfactory.fragment;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fenjin.sandfactory.R;
import com.fenjin.sandfactory.activity.AboutActivity;
import com.fenjin.sandfactory.activity.EditAppActivity;
import com.fenjin.sandfactory.activity.LoginActivity;
import com.fenjin.sandfactory.activity.PasswordActivity;
import com.fenjin.sandfactory.activity.PersonalInfoActivity;
import com.fenjin.sandfactory.adapter.AppAdapter;
import com.fenjin.sandfactory.adapter.GridItemDecoration;
import com.fenjin.sandfactory.databinding.FragmentMeBinding;
import com.fenjin.sandfactory.viewmodel.MeViewModel;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;

/**
 * A simple {@link Fragment} subclass.
 */
public class MeFragment extends BaseFragment {


    public MeFragment() {
        // Required empty public constructor
    }

    private MeViewModel viewModel;

    private AppAdapter appAdapter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(MeViewModel.class);
        init();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        FragmentMeBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_me, container, false);

        binding.setViewModel(viewModel);

        binding.setPersonalInfo(viewModel.dataRepository.getPersonalInfo());

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        recyclerView.addItemDecoration(new GridItemDecoration());
        appAdapter = new AppAdapter(getActivity().getApplicationContext());
        appAdapter.setEditMode(false);
        appAdapter.setItems(viewModel.dataRepository.getPackageNames());
        appAdapter.setOnClickListener(new AppAdapter.onClickListener() {
            @Override
            public void addApp() {

            }

            @Override
            public void deleteApp(int position) {

            }

            @Override
            public void startApp(int position) {
                PackageManager packageManager = getActivity().getPackageManager();

                Intent intent = packageManager.getLaunchIntentForPackage(appAdapter.getItems().get(position));

                if (intent == null) {

                    showToast("应用启动失败，请检查是否安装");

                    return;

                }

                startActivity(intent);
            }
        });

        recyclerView.setAdapter(appAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        appAdapter.setItems(viewModel.dataRepository.getPackageNames());
        appAdapter.notifyDataSetChanged();
    }

    private void init(){
        viewModel.showLogoutDialog.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                new QMUIDialog.MessageDialogBuilder(getActivity())
                        .setTitle("提示")
                        .setMessage("确定要退出登录吗？")
                        .addAction("取消", new QMUIDialogAction.ActionListener() {
                            @Override
                            public void onClick(QMUIDialog dialog, int index) {
                                dialog.dismiss();
                            }
                        })
                        .addAction(0, "退出", QMUIDialogAction.ACTION_PROP_NEGATIVE, new QMUIDialogAction.ActionListener() {
                            @Override
                            public void onClick(QMUIDialog dialog, int index) {
                                dialog.dismiss();
                                viewModel.logout();
                                startActivity(new Intent(getActivity(), LoginActivity.class));
                                getActivity().finish();
                            }
                        })
                        .show();
            }
        });

        viewModel.startAboutActivity.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                startActivity(new Intent(getActivity(), AboutActivity.class));
            }
        });

        viewModel.startPasswordActivity.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                startActivity(new Intent(getActivity(), PasswordActivity.class));
            }
        });

        viewModel.startPersonalInfoActivity.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                startActivity(new Intent(getActivity(), PersonalInfoActivity.class));
            }
        });

        viewModel.startEditAppActivity.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                startActivity(new Intent(getActivity(), EditAppActivity.class));
            }
        });

    }

}
