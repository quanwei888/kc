package com.kanci.ui;

import android.content.Context;

import com.kanci.ui.selectbook.SelectBookActivity;

public class ActivityMgr {
    public static void gotoSelectBook(BaseViewModel.View view) {
        Context context = view.getContext();
        context.startActivity(SelectBookActivity.newIntent(view.getContext()));
    }
}
