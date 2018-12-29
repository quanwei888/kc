package com.kanci.view.home;

import android.os.Bundle;

import com.kanci.BR;
import com.kanci.R;
import com.kanci.databinding.ActivityBookWordBinding;

import me.goldze.mvvmhabit.base.BaseActivity;

public class BookWordActivity extends BaseActivity<ActivityBookWordBinding, BookWordViewModel> {
    @Override
    public int initContentView(Bundle bundle) {
        return R.layout.activity_book_word;
    }

    @Override
    public int initVariableId() {
        return BR.vm;
    }

    @Override
    public void initData() {
    }

}
