package com.fenjin.sandfactory.fragment;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.fenjin.data.entity.ChengZhongRecord;
import com.fenjin.sandfactory.R;
import com.fenjin.sandfactory.adapter.ChengZhongListAdapter;
import com.fenjin.sandfactory.viewmodel.QueryViewModel;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class QueryFragment extends Fragment {


    public QueryFragment() {
        // Required empty public constructor
    }

    private QueryViewModel viewModel;

    private ListView listView;

    private ChengZhongListAdapter adapter;

    private QMUITipDialog loginDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(QueryViewModel.class);
        adapter = new ChengZhongListAdapter();
        init();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_query, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listView = view.findViewById(R.id.list_view);
        listView.setAdapter(adapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        viewModel.loadChengZhongRecordList();
    }

    private void init(){
        viewModel.chengZhongRecordListLive.observe(this, new Observer<List<ChengZhongRecord>>() {
            @Override
            public void onChanged(@Nullable List<ChengZhongRecord> chengZhongRecords) {
                if (chengZhongRecords != null){
                    adapter.setChengZhongRecordList(chengZhongRecords);
                    adapter.notifyDataSetChanged();
                }
            }
        });

        viewModel.loading.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if (aBoolean == null) return;
                if (aBoolean){
                    if (loginDialog == null){
                        loginDialog = new QMUITipDialog.Builder(getActivity())
                                .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
                                .setTipWord("正在加载")
                                .create();
                        loginDialog.setCancelable(false);
                    }
                    loginDialog.show();
                }else {
                    if (loginDialog != null && loginDialog.isShowing()){
                        loginDialog.dismiss();
                    }
                }
            }
        });
    }

}
