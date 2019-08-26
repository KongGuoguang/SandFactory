package com.fenjin.sandfactory.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;

/**
 * Author:kongguoguang
 * Date:2019/8/25
 * Time:11:16
 * Summary:
 */
public class StatisticQueryItemDecoration extends RecyclerView.ItemDecoration {

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        float density = getDensity(parent.getContext());
        outRect.bottom = (int) (10 * density);
//        int position = ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewLayoutPosition();
//        if (position > 0) {
//            float density = getDensity(parent.getContext());
//            int count = parent.getAdapter().getItemCount();
//            if (position < count - 1) {
//                outRect.bottom = (int) (10 * density);
//            }
//        }
    }

    private float getDensity(Context context) {
        Resources resources = context.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        return dm.density;
    }
}
