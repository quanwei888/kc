package com.kanci.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.kanci.ui.login.LoginActivity;
import com.kanci.utils.AppSession;

public abstract class NonAuthActivity<ActivityMainBinding, MainViewModel> extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setup();
    }
}
