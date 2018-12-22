package com.kanci.ui.home;

import android.os.Bundle;
import android.view.View;

import com.kanci.data.model.bean.Book;
import com.kanci.data.model.bean.Task;
import com.kanci.ui.base.BaseActivity;
import com.kanci.ui.base.BaseViewModel;

public class HomeActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((HomeViewModel) VM()).doLoadTask();
    }

    @Override
    public int getLayoutId() {
        return 0;
    }

    @Override
    public int getBindingId() {
        return 0;
    }

    @Override
    public BaseViewModel createViewModel() {
        return new HomeViewModel(this);
    }

    @Override
    public HomeViewModel VM() {
        return VM();
    }

    public void onStartClick(View view) {

    }

    public void onModPlanClick(View view) {

    }

    public void onLoadTask(Task task) {
        if (task != null) {
            VM().doRefreshTask();
        }

    }

    public void onLoadBook(Book book) {
        if (book != null) {
            VM().doRefreshBook(book.id);
        }
    }
}
