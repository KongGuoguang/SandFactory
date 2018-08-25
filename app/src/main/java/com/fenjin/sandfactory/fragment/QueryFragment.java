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
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.fenjin.data.entity.ChengZhongRecord;
import com.fenjin.sandfactory.R;
import com.fenjin.sandfactory.activity.DetailActivity;
import com.fenjin.sandfactory.activity.LoginActivity;
import com.fenjin.sandfactory.databinding.FragmentQueryBinding;
import com.fenjin.sandfactory.util.ErrorCodeUtil;
import com.fenjin.sandfactory.viewmodel.QueryViewModel;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;

/**
 * A simple {@link Fragment} subclass.
 */
public class QueryFragment extends BaseFragment {


    public QueryFragment() {
        // Required empty public constructor
    }

    private QueryViewModel viewModel;

    private QMUITipDialog loadingDialog;

    //Fragment的View加载完毕的标记
    protected boolean isViewCreated;

    private boolean isBottom;

    private TextView noMoreData;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(QueryViewModel.class);
        registerObserver();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        FragmentQueryBinding binding = DataBindingUtil.inflate(inflater,R.layout.fragment_query, container, false);
        binding.setViewModel(viewModel);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ListView listView = view.findViewById(R.id.list_view);

        View footerView = LayoutInflater.from(getContext()).inflate(R.layout.layout_foot, listView, false);
        listView.addFooterView(footerView);

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int scrollState) {
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    if (isBottom && !viewModel.lastPage.getValue()) {
                        // 加载下一页数据
                        viewModel.loadNextPageChengZhongRecords();

                    }
                }

            }

            @Override
            public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                // 说明:
                // fistVisibleItem:表示划出屏幕的ListView子项个数
                // visibleItemCount:表示屏幕中正在显示的ListView子项个数
                // totalItemCount:表示ListView子项的总数
                // 前两个相加==最后一个说明ListView滑到底部
                isBottom = firstVisibleItem + visibleItemCount == totalItemCount;

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ChengZhongRecord record = (ChengZhongRecord) viewModel.adapter.getItem(i);
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                intent.putExtra("record",record);
                startActivity(intent);
            }
        });

        noMoreData = footerView.findViewById(R.id.tv_no_more_data);

        isViewCreated = true;
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
                    if (loadingDialog != null && loadingDialog.isShowing()){
                        loadingDialog.dismiss();
                    }
                }
            }
        });

        viewModel.lastPage.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if (aBoolean){
                    noMoreData.setText(R.string.no_more_data);
                }else {
                    noMoreData.setText("");
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
                    viewModel.dataRepository.saveToken("");
                }
            }
        });

        viewModel.errorMessage.observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                showErrorDialog(s);
            }
        });
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        //isVisibleToUser这个boolean值表示:该Fragment的UI 用户是否可见
        if (isVisibleToUser && isViewCreated && viewModel.currentPage == 0){
            viewModel.loadFirstPageChengZhongRecords();
        }
    }

}
