package com.kanci.ui.book.wordlist;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.kanci.R;
import com.kanci.data.model.db.Word;
import com.kanci.databinding.ActivityBookWordlistBinding;
import com.kanci.ui.base.BaseActivity;
import com.kanci.ui.base.BaseViewModel;
import com.kanci.ui.book.wordlist.fragment.Adapter;
import com.kanci.ui.book.wordlist.fragment.ListFragment;

import java.util.ArrayList;
import java.util.List;

public class WordListActivity extends BaseActivity<ActivityBookWordlistBinding, WordListViewModel> {
    Adapter adapter;
    TabLayout tabLayout;
    ViewPager viewPager;

    @Override
    public int getLayoutId() {
        return R.layout.activity_book_wordlist;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int bookId = getIntent().getIntExtra("bookId", 0);

        tabLayout = ((ActivityBookWordlistBinding) getBinding()).tabView;
        viewPager = ((ActivityBookWordlistBinding) getBinding()).pagerView;

        List<Fragment> fragments = new ArrayList<>();
        ListFragment fragment;
        Bundle args;
        fragment = new ListFragment();
        args = new Bundle();
        args.putInt("bookId", bookId);
        args.putInt("tag", 0);
        fragment.setArguments(args);
        fragments.add(fragment);
        fragment = new ListFragment();
        args = new Bundle();
        args.putInt("bookId", bookId);
        args.putInt("tag", 1);
        fragment.setArguments(args);
        fragments.add(fragment);

        List<String> tabTitles = new ArrayList<>();
        tabTitles.add("未砍");
        tabTitles.add("已砍");

        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager(), fragments, tabTitles);
        viewPager.setAdapter(adapter);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }

        });
        tabLayout.setupWithViewPager(viewPager);
    }

    public void onLoadBookList(List<Word> data) {
        /*
        RecyclerView rv = ((ActivityBookWordlistBinding) V()).listView;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(linearLayoutManager);

        adapter = new Adapter(this);
        adapter.addAll(data);
        rv.setAdapter(adapter);
        */
    }

    public void onSetWord() {
    }
}
