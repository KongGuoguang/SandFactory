package com.fenjin.sandfactory.adapter;

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

        ViewHolder viewHolder;

        Channel channel = channelList.get(i);

        if (view == null){
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_channel, viewGroup, false);
            viewHolder = new ViewHolder();
            viewHolder.snapImage = view.findViewById(R.id.image_snap);
            viewHolder.playImage = view.findViewById(R.id.image_play);
            viewHolder.channelName = view.findViewById(R.id.text_channel_name);
            viewHolder.offline = view.findViewById(R.id.tv_off_line);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.channelName.setText(channel.getName());

        if (channel.getOnline() == 1){
            viewHolder.playImage.setVisibility(View.VISIBLE);
            viewHolder.offline.setVisibility(View.INVISIBLE);
        }else {
            viewHolder.playImage.setVisibility(View.INVISIBLE);
            viewHolder.offline.setVisibility(View.VISIBLE);
        }

        if (!TextUtils.isEmpty(channel.getSnapURL())){
            Glide.with(view)
                    .load(SERVER_ADDRESS + channel.getSnapURL())
                    .apply(options)
                    .into(viewHolder.snapImage);
        }else {
            Glide.with(view)
                    .load(android.R.color.darker_gray)
                    .into(viewHolder.snapImage);
        }

        return view;
    }


    private class ViewHolder{
        ImageView snapImage;

        ImageView playImage;

        TextView channelName;

        TextView offline;
    }

    private RequestOptions options = new RequestOptions()

            .skipMemoryCache(true) //禁用内存缓存

            .diskCacheStrategy(DiskCacheStrategy.NONE);//硬盘缓存功能
}
