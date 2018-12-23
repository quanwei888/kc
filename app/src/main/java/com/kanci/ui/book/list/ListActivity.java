package com.kanci.ui.book.list;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.kanci.R;
import com.kanci.data.model.bean.Book;
import com.kanci.databinding.ActivityBookListBinding;
import com.kanci.ui.base.BaseActivity;
import com.kanci.ui.base.BaseViewModel;
import com.kanci.ui.book.add.AddActivity;

import java.util.List;

public class ListActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        VM().doLoadBookList();
    }

    @Override
    public ListViewModel VM() {
        return (ListViewModel) super.VM();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_book_list;
    }

    @Override
    public BaseViewModel createViewModel() {
        return new ListViewModel(this);
    }

    public void onLoadBookList(List<Book> bookList) {
        RecyclerView rv = ((ActivityBookListBinding) getBinding()).listView;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(linearLayoutManager);

        Adapter adapter = new Adapter(this);
        adapter.addAll(bookList);
        rv.setAdapter(adapter);
    }

    public void onBookItemClick(int bookId) {
        Intent intent = new Intent(this, AddActivity.class);
        intent.putExtra("bookId", bookId);
        startActivityForResult(intent, 0);
    }
}
