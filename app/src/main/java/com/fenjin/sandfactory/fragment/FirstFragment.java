package com.fenjin.sandfactory.fragment;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.fenjin.sandfactory.R;
import com.fenjin.sandfactory.activity.LoginActivity;
import com.fenjin.sandfactory.databinding.FragmentFirstBinding;
import com.fenjin.sandfactory.util.ErrorCodeUtil;
import com.fenjin.sandfactory.viewmodel.FirstViewModel;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;
import com.qmuiteam.qmui.widget.pullRefreshLayout.QMUIPullRefreshLayout;

/**
 * A simple {@link Fragment} subclass.
 */
public class FirstFragment extends Fragment {


    public FirstFragment() {
        // Required empty public constructor
    }

    private FirstViewModel viewModel;

    private QMUITipDialog loadingDialog;

    private QMUIPullRefreshLayout pullRefreshLayout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(FirstViewModel.class);
        registerObserver();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FragmentFirstBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_first, container, false);
        binding.setViewModel(viewModel);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        pullRefreshLayout = view.findViewById(R.id.layout_pull_refresh);
        pullRefreshLayout.setOnPullListener(new QMUIPullRefreshLayout.OnPullListener() {
            @Override
            public void onMoveTarget(int offset) {
            }

            @Override
            public void onMoveRefreshView(int offset) {
            }

            @Override
            public void onRefresh() {
                viewModel.loadChengZhongRecordList();
            }
        });

        viewModel.loadChengZhongRecordList();
    }

    private void registerObserver(){

        viewModel.loading.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if (aBoolean == null) return;
                if (aBoolean){
                    if (loadingDialog == null){
                        loadingDialog = new QMUITipDialog.Builder(getActivity())
                                .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
                                .setTipWord("正在加载")
                                .create();
                        loadingDialog.setCancelable(true);
                    }
                    loadingDialog.show();
                }else {
                    pullRefreshLayout.finishRefresh();
                    if (loadingDialog != null && loadingDialog.isShowing()){
                        loadingDialog.dismiss();
                    }
                }
            }
        });

        viewModel.errorCode.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer integer) {

                if (integer == null) return;

                if (integer == ErrorCodeUtil.TOKEN_TIME_OUT){
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                    getActivity().finish();
                    ToastUtils.showShort("登录信息超时，请重新登录");
                }
            }
        });
    }

}
