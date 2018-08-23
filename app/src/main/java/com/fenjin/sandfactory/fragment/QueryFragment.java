package com.fenjin.sandfactory.fragment;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.TextView;

import com.fenjin.sandfactory.R;
import com.fenjin.sandfactory.databinding.FragmentQueryBinding;
import com.fenjin.sandfactory.viewmodel.QueryViewModel;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;

/**
 * A simple {@link Fragment} subclass.
 */
public class QueryFragment extends Fragment {


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
        init();
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
        listView.addFooterView(footerView, null, true);
        listView.setFooterDividersEnabled(false);

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

        noMoreData = footerView.findViewById(R.id.tv_no_more_data);

        isViewCreated = true;
    }

    private void init(){

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
