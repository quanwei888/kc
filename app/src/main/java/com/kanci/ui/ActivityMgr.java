package com.kanci.ui;

import android.content.Context;
import android.content.Intent;

import com.kanci.data.model.bean.Book;
import com.kanci.ui.book.add.BookAddActivity;
import com.kanci.ui.book.plan.BookPlanActivity;

public class ActivityMgr {
    public static void gotoBookAdd(BaseViewModel.View view) {
        Context context = view.getContext();
        context.startActivity(BookAddActivity.newIntent(view.getContext()));
    }

    public static void gotoBookPlan(BaseViewModel.View view, Book book) {
        Context context = view.getContext();
        Intent intent = BookPlanActivity.newIntent(view.getContext());
        intent.putExtra("book", book);
        context.startActivity(BookPlanActivity.newIntent(view.getContext()));
    }
}
