package com.fenjin.sandfactory.adapter;

import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.fenjin.data.entity.ChengZhongRecord;
import com.fenjin.sandfactory.R;
import com.fenjin.sandfactory.databinding.ItemChengZhongRecordBinding;

import java.util.List;

/**
 * Author:kongguoguang
 * Date:2018-08-02
 * Time:19:04
 * Summary:
 */
public class ChengZhongListAdapter extends BaseAdapter {

    private List<ChengZhongRecord> chengZhongRecordList;

    public ChengZhongListAdapter(){}

    public ChengZhongListAdapter(List<ChengZhongRecord> chengZhongRecordList) {
        this.chengZhongRecordList = chengZhongRecordList;
    }

    public void setChengZhongRecordList(List<ChengZhongRecord> chengZhongRecordList) {
        this.chengZhongRecordList = chengZhongRecordList;
    }

    @Override
    public int getCount() {
        return chengZhongRecordList == null ? 0 : chengZhongRecordList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ChengZhongRecord  chengZhongRecord = chengZhongRecordList.get(i);
        ItemChengZhongRecordBinding binding;
        if (view == null){
            binding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()),
                    R.layout.item_cheng_zhong_record, viewGroup, false);
            view = binding.getRoot();
        }else {
            binding = DataBindingUtil.getBinding(view);
        }

        if (binding != null) {
            binding.setViewModel(chengZhongRecord);
        }

        return view;
    }
}
