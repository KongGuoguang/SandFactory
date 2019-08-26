package com.fenjin.sandfactory.adapter;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.fenjin.data.bean.StatisticQueryDetailItem;
import com.fenjin.sandfactory.R;
import com.fenjin.sandfactory.databinding.ItemStatisticQueryDetailBinding;

import java.util.List;

public class StatisticQueryDetailAdapter extends RecyclerView.Adapter<StatisticQueryDetailAdapter.StatisticQueryDetailViewHolder> {

    private List<StatisticQueryDetailItem> items;

    public void setItems(List<StatisticQueryDetailItem> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public StatisticQueryDetailViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ItemStatisticQueryDetailBinding binding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()),
                R.layout.item_statistic_query_detail, viewGroup, false);
        return new StatisticQueryDetailViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull StatisticQueryDetailViewHolder viewHolder, int i) {
        viewHolder.binding.setItem(items.get(i));
    }

    @Override
    public int getItemCount() {
        return items == null ? 0 : items.size();
    }

    class StatisticQueryDetailViewHolder extends RecyclerView.ViewHolder {
        ItemStatisticQueryDetailBinding binding;

        public StatisticQueryDetailViewHolder(ItemStatisticQueryDetailBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
