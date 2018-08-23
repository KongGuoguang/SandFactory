package com.fenjin.sandfactory.adapter;

import android.databinding.DataBindingUtil;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.fenjin.data.entity.Channel;
import com.fenjin.sandfactory.R;
import com.fenjin.sandfactory.databinding.ItemChannelBinding;

import java.util.List;

public class ChannelListAdapter extends BaseAdapter {

    private String SERVER_ADDRESS = "http://112.35.23.101:10800/";

    private List<Channel> channelList;

    public void setChannelList(List<Channel> channelList) {
        this.channelList = channelList;
    }

    @Override
    public int getCount() {
        return channelList == null ? 0 : channelList.size();
    }

    @Override
    public Object getItem(int i) {
        return channelList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        Channel channel = channelList.get(i);

        ItemChannelBinding binding;

        if (view == null){
            binding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()),
                    R.layout.item_channel, viewGroup, false);
            view = binding.getRoot();
        }else {
            binding = DataBindingUtil.getBinding(view);
        }

        if (binding == null) return view;

        binding.setViewModel(channel);

        if (i == 0){
            binding.viewHeader.setVisibility(View.VISIBLE);
        }else {
            binding.viewHeader.setVisibility(View.GONE);
        }


        if (!TextUtils.isEmpty(channel.getSnapURL())){
            Glide.with(view)
                    .load(SERVER_ADDRESS + channel.getSnapURL())
                    .apply(options)
                    .into(binding.imageSnap);
        }else {
            Glide.with(view)
                    .load(android.R.color.darker_gray)
                    .into(binding.imageSnap);
        }

        return view;
    }


    private RequestOptions options = new RequestOptions()

            .skipMemoryCache(true) //禁用内存缓存

            .diskCacheStrategy(DiskCacheStrategy.NONE);//硬盘缓存功能
}
