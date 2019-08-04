package com.fenjin.sandfactory.view;

import android.content.Context;
import android.support.annotation.ColorRes;
import android.support.v4.content.ContextCompat;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.qmuiteam.qmui.widget.QMUIWrapContentListView;
import com.qmuiteam.qmui.widget.popup.QMUIPopup;

public class QMUIListPopupWithDivider extends QMUIPopup {
    private BaseAdapter mAdapter;

    /**
     * 构造方法。
     *
     * @param context   传入一个 Context。
     * @param direction Popup 的方向，为 {@link QMUIPopup#DIRECTION_NONE}, {@link QMUIPopup#DIRECTION_TOP} 和 {@link QMUIPopup#DIRECTION_BOTTOM} 中的其中一个值。
     * @param adapter   列表的 Adapter
     */
    public QMUIListPopupWithDivider(Context context, @Direction int direction, BaseAdapter adapter) {
        super(context, direction);
        mAdapter = adapter;
    }

    public void create(int width, int maxHeight, @ColorRes int dividerColor, AdapterView.OnItemClickListener onItemClickListener) {
        ListView listView = new QMUIWrapContentListView(mContext, maxHeight);
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(width, maxHeight);
        listView.setLayoutParams(lp);
        listView.setAdapter(mAdapter);
        listView.setVerticalScrollBarEnabled(false);
        listView.setOnItemClickListener(onItemClickListener);
        listView.setDividerHeight(1);
        listView.setDivider(ContextCompat.getDrawable(mContext, dividerColor));
        setContentView(listView);
    }
}
