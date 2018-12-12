package com.kanci.ui;

import android.content.Context;

import com.kanci.ui.book.addbook.AddBookActivity;

public class ActivityMgr {
    public static void gotoAddBook(BaseViewModel.View view) {
        Context context = view.getContext();
        context.startActivity(AddBookActivity.newIntent(view.getContext()));
    }
}
