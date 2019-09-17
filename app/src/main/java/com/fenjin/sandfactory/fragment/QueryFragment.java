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
import android.widget.PopupWindow;
import android.widget.TextView;

import com.fenjin.data.entity.ChengZhongRecord;
import com.fenjin.sandfactory.R;
import com.fenjin.sandfactory.activity.DetailActivity;
import com.fenjin.sandfactory.databinding.FragmentQueryBinding;
import com.fenjin.sandfactory.viewmodel.QueryFragmentViewModel;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;

/**
 * A simple {@link Fragment} subclass.
 */
public class QueryFragment extends BaseFragment implements View.OnClickListener {


    public QueryFragment() {
        // Required empty public constructor
    }

    private QueryFragmentViewModel viewModel;

    private QMUITipDialog loadingDialog;

    //Fragment的View加载完毕的标记
    protected boolean isViewCreated;

    private boolean isBottom;

    private TextView noMoreData;

    private TextView queryCondition;

    private PopupWindow popupWindow;

    private View popupWindowView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(QueryFragmentViewModel.class);
        registerObserver();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        popupWindowView = inflater.inflate(R.layout.layout_query_condition, container, false);

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

        queryCondition = view.findViewById(R.id.tv_query_condition);
        queryCondition.setOnClickListener(this);

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

                if (aBoolean == null) return;

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
                dealErrorCode(integer);
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
        if (isVisibleToUser && isViewCreated && viewModel.totalCount == 0) {
            viewModel.loadFirstPageChengZhongRecords();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_query_condition:
                if (popupWindow == null) {
                    popupWindow = new PopupWindow(popupWindowView, QMUIDisplayHelper.dp2px(getContext(), 70),
                            ViewGroup.LayoutParams.WRAP_CONTENT);

                    popupWindow.setFocusable(true);
                    popupWindow.setOutsideTouchable(true);

                    TextView bill = popupWindowView.findViewById(R.id.tv_bill);
                    bill.setOnClickListener(this);

                    TextView carNO = popupWindowView.findViewById(R.id.tv_car_no);
                    carNO.setOnClickListener(this);

                    TextView goodsName = popupWindowView.findViewById(R.id.tv_goods_name);
                    goodsName.setOnClickListener(this);

                    TextView shaChang = popupWindowView.findViewById(R.id.tv_site_name);
                    shaChang.setOnClickListener(this);
                }

                popupWindow.showAsDropDown(queryCondition, 0, QMUIDisplayHelper.dp2px(getContext(), 10));
                break;
            case R.id.tv_bill:
                popupWindow.dismiss();
                queryCondition.setText(R.string.bill);
                viewModel.searchKeywords.set("");
                viewModel.searchKeywordsHint.set(getString(R.string.search_bill_hint));
                viewModel.searchType = QueryFragmentViewModel.SEARCH_TYPE_BILL;
                break;
            case R.id.tv_car_no:
                popupWindow.dismiss();
                queryCondition.setText(R.string.car_no);
                viewModel.searchKeywords.set("");
                viewModel.searchKeywordsHint.set(getString(R.string.search_car_no_hint));
                viewModel.searchType = QueryFragmentViewModel.SEARCH_TYPE_CAR_NO;
                break;
            case R.id.tv_goods_name:
                popupWindow.dismiss();
                queryCondition.setText(R.string.goods_name);
                viewModel.searchKeywords.set("");
                viewModel.searchKeywordsHint.set(getString(R.string.search_goods_name_hint));
                viewModel.searchType = QueryFragmentViewModel.SEARCH_TYPE_GOODS_NAME;
                break;
            case R.id.tv_site_name:
                popupWindow.dismiss();
                queryCondition.setText(R.string.sha_chang);
                viewModel.searchKeywords.set("");
                viewModel.searchKeywordsHint.set(getString(R.string.search_sha_chang_hint));
                viewModel.searchType = QueryFragmentViewModel.SEARCH_TYPE_SITE_NAME;
                break;
        }
    }
}
