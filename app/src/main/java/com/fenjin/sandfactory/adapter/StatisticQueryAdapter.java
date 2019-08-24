package com.fenjin.sandfactory.adapter;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.fenjin.data.bean.StatisticsQueryItem;
import com.fenjin.sandfactory.R;
import com.fenjin.sandfactory.databinding.ItemStatisticQueryBinding;

import java.util.List;

/**
 * Author:kongguoguang
 * Date:2019/8/24
 * Time:17:31
 * Summary:
 */
public class StatisticQueryAdapter extends RecyclerView.Adapter<StatisticQueryAdapter.StatisticQueryViewHolder> {

    private List<StatisticsQueryItem> items;

    private CheckDetailListener checkDetailListener;

    private int type;

    public StatisticQueryAdapter(int type) {
        this.type = type;
    }

    public void setItems(List<StatisticsQueryItem> items) {
        this.items = items;
    }

    public void setCheckDetailListener(CheckDetailListener checkDetailListener) {
        this.checkDetailListener = checkDetailListener;
    }

    @NonNull
    @Override
    public StatisticQueryAdapter.StatisticQueryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ItemStatisticQueryBinding binding = DataBindingUtil.inflate(LayoutInflater.from(
                viewGroup.getContext()), R.layout.item_statistic_query, viewGroup, false);
        return new StatisticQueryViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull StatisticQueryAdapter.StatisticQueryViewHolder viewHolder, int i) {
        viewHolder.binding.setType(1);
        viewHolder.binding.setItem(items.get(i));
        viewHolder.binding.checkbox.setTag(i);
        viewHolder.binding.checkDetail.setTag(i);
    }

    @Override
    public int getItemCount() {
        return items == null ? 0 : items.size();
    }

    class StatisticQueryViewHolder extends RecyclerView.ViewHolder {
        ItemStatisticQueryBinding binding;

        public StatisticQueryViewHolder(ItemStatisticQueryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        int position = (int) buttonView.getTag();
        items.get(position).checked.set(isChecked);
    }

    public void onClick(View view) {
        int position = (int) view.getTag();
        if (checkDetailListener != null) {
            checkDetailListener.checkDetail(position);
        }
    }

    public interface CheckDetailListener {
        void checkDetail(int position);
    }
}
