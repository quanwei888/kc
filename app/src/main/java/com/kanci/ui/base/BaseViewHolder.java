package com.kanci.ui.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by qw on 18-12-8.
 */

public abstract class BaseViewHolder extends RecyclerView.ViewHolder {
    public BaseViewHolder(View itemView) {
        super(itemView);
    }

    public abstract void onBind(int position);
}
