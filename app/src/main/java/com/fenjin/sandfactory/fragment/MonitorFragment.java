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
import android.widget.AdapterView;
import android.widget.GridView;

import com.fenjin.data.entity.Channel;
import com.fenjin.sandfactory.R;
import com.fenjin.sandfactory.activity.PlayActivity;
import com.fenjin.sandfactory.databinding.FragmentMonitorBinding;
import com.fenjin.sandfactory.viewmodel.MonitorViewModel;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;
import com.qmuiteam.qmui.widget.pullRefreshLayout.QMUIPullRefreshLayout;

/**
 * A simple {@link Fragment} subclass.
 */
public class MonitorFragment extends BaseFragment {


    public MonitorFragment() {
        // Required empty public constructor
    }

    private MonitorViewModel viewModel;


    private QMUITipDialog loadingDialog;

    //Fragment的View加载完毕的标记
    protected boolean isViewCreated;

    private QMUIPullRefreshLayout pullRefreshLayout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(MonitorViewModel.class);
        registerObserver();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        FragmentMonitorBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_monitor, container, false);
        binding.setViewModel(viewModel);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        GridView gridView = view.findViewById(R.id.grid_view);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Channel channel = (Channel) viewModel.adapter.getItem(i);
                if (channel.getOnline() == 1){
                    Intent intent = new Intent(getActivity(), PlayActivity.class);
                    intent.putExtra("channel", channel.getChannel());
                    intent.putExtra("channelName", channel.getName());
                    startActivity(intent);
                }
            }
        });

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
                viewModel.getAllChannel();
            }
        });
        isViewCreated = true;
    }

    private void registerObserver() {

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
                dealErrorCode(integer);
            }
        });
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        //isVisibleToUser这个boolean值表示:该Fragment的UI 用户是否可见
        if (isVisibleToUser && isViewCreated && viewModel.adapter.getCount() == 0) {
            viewModel.getAllChannel();
        }
    }
}
