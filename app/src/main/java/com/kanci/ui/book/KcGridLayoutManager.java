package com.kanci.ui.book;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by qw on 18-12-13.
 */

public class KcGridLayoutManager extends GridLayoutManager {
    public KcGridLayoutManager(Context context, int spanCount) {
        super(context, spanCount);
    }

    @Override
    public void onMeasure(RecyclerView.Recycler recycler, RecyclerView.State state, final int widthSpec, final int heightSpec) {

    }
}