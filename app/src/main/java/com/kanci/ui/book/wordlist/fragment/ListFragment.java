package com.kanci.ui.book.wordlist.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kanci.R;
import com.kanci.data.model.db.Word;
import com.kanci.databinding.FragmentBookWordlistBinding;
import com.kanci.ui.base.BaseFragment;
import com.kanci.ui.base.BaseViewModel;

import java.util.List;

/**
 * Created by qw on 18-12-23.
 */

public class ListFragment extends BaseFragment {

    @Override
    public int getLayoutId() {
        return R.layout.fragment_book_wordlist;
    }

    @Override
    public BaseViewModel createViewModel() {
        return new ListViewModel(this);
    }

    @Override
    public ListViewModel VM() {
        return (ListViewModel) super.VM();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);
        int bookId = getArguments().getInt("bookId");
        int tag = getArguments().getInt("tag");
        VM().doLoadBookWordList(bookId, tag);
        return v;
    }

    public void onLoadBookList(List<Word> data) {
        RecyclerView rv = ((FragmentBookWordlistBinding) getBinding()).listView;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(linearLayoutManager);

        Adapter adapter = new Adapter(this);
        adapter.addAll(data);
        rv.setAdapter(adapter);
    }


}
