package com.fenjin.sandfactory.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fenjin.sandfactory.R;
import com.fenjin.sandfactory.databinding.ItemAddAppBinding;
import com.fenjin.sandfactory.databinding.ItemAppBinding;
import com.fenjin.sandfactory.util.AppInfoUtil;

import java.util.List;

public class AppAdapter extends RecyclerView.Adapter {

    private Context context;

    private List<String> items;

    private boolean isEditMode;

    private final int VIEW_TYPE_ADD_APP = 1;

    private onClickListener onClickListener;

    public AppAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (i == VIEW_TYPE_ADD_APP) {
            ItemAddAppBinding binding = DataBindingUtil.inflate(LayoutInflater.from(
                    viewGroup.getContext()), R.layout.item_add_app, viewGroup, false);
            return new AddAppViewHolder(binding);
        }

        ItemAppBinding binding = DataBindingUtil.inflate(LayoutInflater.from(
                viewGroup.getContext()), R.layout.item_app, viewGroup, false);

        return new AppViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (viewHolder instanceof AppViewHolder) {
            ItemAppBinding binding = ((AppViewHolder) viewHolder).binding;
            binding.setAdapter(this);
            binding.setIsEditMode(isEditMode);
            binding.setAppInfo(AppInfoUtil.getAppInfo(context, items.get(i)));
            binding.icon.setTag(i);
            binding.name.setTag(i);
            binding.deleteApp.setTag(i);
        }

        if (viewHolder instanceof AddAppViewHolder) {
            ItemAddAppBinding binding = ((AddAppViewHolder) viewHolder).binding;
            binding.setAdapter(this);
            binding.layoutAddApp.setTag(i);
        }
    }


    @Override
    public int getItemCount() {
        int count;


        if (isEditMode) {
            count = items == null ? 1 : items.size() + 1;
        } else {
            count = items == null ? 0 : items.size();
        }
        return count;
    }

    @Override
    public int getItemViewType(int position) {
        if (isEditMode && position == getItemCount() - 1) {//最后一个viewType为#VIEW_TYPE_ADD_APP
            return VIEW_TYPE_ADD_APP;
        }
        return super.getItemViewType(position);
    }


    public void onClick(View view) {
        if (onClickListener == null) return;
        int position = (int) view.getTag();
        if (isEditMode) {
            if (position == getItemCount() - 1) {
                onClickListener.addApp();
            }

            if (view.getId() == R.id.delete_app) {
                onClickListener.deleteApp(position);
            }
        } else {
            onClickListener.startApp(position);
        }

    }

    class AppViewHolder extends RecyclerView.ViewHolder {

        ItemAppBinding binding;

        public AppViewHolder(ItemAppBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    class AddAppViewHolder extends RecyclerView.ViewHolder {

        ItemAddAppBinding binding;

        public AddAppViewHolder(ItemAddAppBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public interface onClickListener {
        void addApp();

        void deleteApp(int position);

        void startApp(int position);
    }

    public void setOnClickListener(AppAdapter.onClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }


    public List<String> getItems() {
        return items;
    }

    public void setItems(List<String> items) {
        this.items = items;
    }

    public void setEditMode(boolean editMode) {
        isEditMode = editMode;
    }
}
