package com.fenjin.sandfactory.adapter;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.fenjin.data.bean.BalanceQueryItem;
import com.fenjin.sandfactory.R;
import com.fenjin.sandfactory.databinding.ItemBalanceQueryBinding;

import java.util.List;

/**
 * Author:kongguoguang
 * Date:2019/8/28
 * Time:19:22
 * Summary:
 */
public class BalanceQueryAdapter extends RecyclerView.Adapter<BalanceQueryAdapter.BalanceQueryViewHolder> {

    private List<BalanceQueryItem> items;

    public BalanceQueryAdapter(List<BalanceQueryItem> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public BalanceQueryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ItemBalanceQueryBinding binding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()),
                R.layout.item_balance_query, viewGroup, false);

        return new BalanceQueryViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull BalanceQueryViewHolder viewHolder, int i) {
        BalanceQueryItem item = items.get(i);
        viewHolder.binding.setItem(item);
    }

    @Override
    public int getItemCount() {
        return items == null ? 0 : items.size();
    }

    class BalanceQueryViewHolder extends RecyclerView.ViewHolder {
        ItemBalanceQueryBinding binding;

        public BalanceQueryViewHolder(ItemBalanceQueryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
