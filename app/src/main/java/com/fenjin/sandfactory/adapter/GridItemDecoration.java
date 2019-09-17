package com.fenjin.sandfactory.adapter;

import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.qmuiteam.qmui.util.QMUIDisplayHelper;

public class GridItemDecoration extends RecyclerView.ItemDecoration {

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.right = QMUIDisplayHelper.dp2px(view.getContext(), 10);
        outRect.bottom = QMUIDisplayHelper.dp2px(view.getContext(), 10);
    }
}
