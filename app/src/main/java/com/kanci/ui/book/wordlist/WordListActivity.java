package com.kanci.ui.book.wordlist;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.kanci.data.model.db.Word;
import com.kanci.databinding.ActivityBookWordlistBinding;
import com.kanci.ui.base.BaseActivity;
import com.kanci.ui.base.BaseViewModel;

import java.util.List;

public class WordListActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        return new WordListViewModel(this);
    }

    public void onLoadBookList(List<Word> data) {
        RecyclerView rv = ((ActivityBookWordlistBinding) getBinding()).listView;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rv.setLayoutManager(linearLayoutManager);

        Adapter adapter = new Adapter(this);
        adapter.addAll(data);
        rv.setAdapter(adapter);
    }

    public void onSetWord() {
    }
}
