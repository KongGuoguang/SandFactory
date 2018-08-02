package com.fenjin.sandfactory.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.fenjin.data.entity.ChengZhongRecord;
import com.fenjin.sandfactory.R;

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
        ViewHolder viewHolder;
        ChengZhongRecord  chengZhongRecord = chengZhongRecordList.get(i);
        if (view == null){
            view = LayoutInflater.from(viewGroup.getContext()).inflate(
                    R.layout.item_cheng_zhong_record, viewGroup, false);
            viewHolder = new ViewHolder();
            viewHolder.xh = view.findViewById(R.id.tv_xh);
            viewHolder.crlx = view.findViewById(R.id.tv_crlx);
            viewHolder.sandName = view.findViewById(R.id.tv_sandName);
            viewHolder.hm = view.findViewById(R.id.tv_hm);
            viewHolder.ch = view.findViewById(R.id.tv_ch);
            viewHolder.mz = view.findViewById(R.id.tv_mz);
            viewHolder.je = view.findViewById(R.id.tv_je);
            viewHolder.jz = view.findViewById(R.id.tv_jz);
            viewHolder.dateTime = view.findViewById(R.id.tv_date_time);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.xh.setText(chengZhongRecord.getXh());
        viewHolder.crlx.setText(chengZhongRecord.getCrlx());
        viewHolder.sandName.setText(chengZhongRecord.getSandName());
        viewHolder.hm.setText(chengZhongRecord.getHm());
        viewHolder.ch.setText("车辆号码:" + chengZhongRecord.getCh());
        viewHolder.mz.setText("毛重：" + chengZhongRecord.getMz());
        viewHolder.je.setText("金额：" + chengZhongRecord.getJe());
        viewHolder.jz.setText("净重：" + chengZhongRecord.getJz());
        viewHolder.dateTime.setText(chengZhongRecord.getRq() + " " + chengZhongRecord.getShijian());

        return view;
    }

    private class ViewHolder{
        TextView xh;
        TextView crlx;
        TextView sandName;
        TextView hm;
        TextView ch;
        TextView mz;
        TextView je;
        TextView jz;
        TextView dateTime;
    }
}
