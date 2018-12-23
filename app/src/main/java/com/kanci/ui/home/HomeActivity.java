package com.kanci.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.kanci.R;
import com.kanci.data.model.bean.Book;
import com.kanci.data.model.bean.Task;
import com.kanci.ui.base.BaseActivity;
import com.kanci.ui.base.BaseViewModel;
import com.kanci.ui.book.add.AddActivity;
import com.kanci.ui.book.wordlist.WordListActivity;
import com.kanci.ui.task.card.CardActivity;

public class HomeActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        VM().doLoadTask();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_home;
    }

    @Override
    public BaseViewModel createViewModel() {
        return new HomeViewModel(this);
    }

    @Override
    public HomeViewModel VM() {
        return (HomeViewModel) super.VM();
    }

    public void onStartClick(View view) {
        Intent intent = new Intent(this, CardActivity.class);
        intent.putExtra("bookId", VM().book.get().bookId);
        startActivity(intent);
    }

    public void onModPlanClick(View view) {
        Intent intent = new Intent(this, AddActivity.class);
        intent.putExtra("bookId", VM().book.get().bookId);
        startActivityForResult(intent, 0);
    }

    public void onWordListClick(View view) {
        Intent intent = new Intent(this, WordListActivity.class);
        intent.putExtra("bookId", VM().book.get().bookId);
        startActivityForResult(intent, 0);
    }

    public void onLoadTask(Task task) {
        VM().doLoadBook(task.bookId);
    }

    public void onLoadBook(Book book) {

    }
}
