package com.fenjin.sandfactory.adapter;

import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.qmuiteam.qmui.util.QMUIDisplayHelper;

/**
 * Author:kongguoguang
 * Date:2019/8/25
 * Time:11:16
 * Summary:通用RecyclerView分割线
 */
public class RecyclerViewItemDecoration extends RecyclerView.ItemDecoration {

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.bottom = QMUIDisplayHelper.dp2px(view.getContext(), 10);
    }
}
