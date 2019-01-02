package com.kanci.view.book.list;

import android.os.Bundle;

import com.kanci.BR;
import com.kanci.R;
import com.kanci.databinding.ActivityBookListBinding;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;

import me.goldze.mvvmhabit.base.BaseActivity;

public class BookListActivity extends BaseActivity<ActivityBookListBinding, BookListViewModel> {
    @Override
    public int initContentView(Bundle bundle) {
        return R.layout.activity_book_list;
    }

    @Override
    public int initVariableId() {
        return BR.vm;
    }
    @Override
    public void initData() {
        QMUIStatusBarHelper.translucent(this);
        binding.topbar.setTitle("单词书");
        viewModel.doLoadBookList();
    }
}
