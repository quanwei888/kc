package com.kanci.ui.book.add;

import android.os.Bundle;
import android.view.View;
import android.widget.NumberPicker;

import com.kanci.ui.base.BaseActivity;
import com.kanci.ui.base.BaseViewModel;

public class AddActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int bookId = getIntent().getIntExtra("bookId", 0);
        VM().doLoadBook(bookId);
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
    public AddViewModel VM() {
        return VM();
    }

    @Override
    public BaseViewModel createViewModel() {
        return new AddViewModel(this);
    }

    public void onAddBook() {
        VM().doCreateTask();
    }

    public void onCreateTask() {
        finish();
    }

    public void onChangePlanClick(NumberPicker picker, int oldVal, int newVal) {
        VM().doChangePlan(newVal);
    }

    public void onAddBookClick(View view) {
        VM().doAddBook();
    }
}
