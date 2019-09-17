package com.fenjin.sandfactory.adapter;

import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.fenjin.data.bean.ChengZhongStatisticsItem;
import com.fenjin.sandfactory.R;
import com.fenjin.sandfactory.databinding.ItemChengZhongStaticBinding;

import java.util.List;

public class ChengZhongStatisticsAdapter extends BaseAdapter {

    private List<ChengZhongStatisticsItem> chengZhongStatisticsItemList;

    @Override
    public int getCount() {

        return chengZhongStatisticsItemList == null ? 0 : chengZhongStatisticsItemList.size();
    }


    public void setChengZhongStatisticsItemList(List<ChengZhongStatisticsItem> chengZhongStatisticsItemList) {
        this.chengZhongStatisticsItemList = chengZhongStatisticsItemList;
    }

    @Override
    public Object getItem(int i) {
        return chengZhongStatisticsItemList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ChengZhongStatisticsItem chengZhongStatisticsItem = chengZhongStatisticsItemList.get(i);
        ItemChengZhongStaticBinding binding;
        if (view == null) {
            binding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()),
                    R.layout.item_cheng_zhong_static, viewGroup, false);
            view = binding.getRoot();
        } else {
            binding = DataBindingUtil.getBinding(view);
        }


        if (binding != null) {
            binding.setViewModel(chengZhongStatisticsItem);
            binding.total.setSelected(true);
        }
        return view;
    }
}
