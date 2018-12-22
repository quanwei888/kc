package com.kanci.ui.demo;

import android.os.Bundle;

import com.kanci.ui.base.BaseActivity;
import com.kanci.ui.base.BaseViewModel;

public class HomeActivity extends BaseActivity {
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
        return null;
    }

}
