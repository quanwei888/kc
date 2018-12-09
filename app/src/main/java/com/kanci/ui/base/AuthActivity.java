package com.kanci.ui.base;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.kanci.BR;
import com.kanci.R;
import com.kanci.data.AppDataManager;
import com.kanci.databinding.ActivityMainBinding;
import com.kanci.ui.login.LoginActivity;
import com.kanci.ui.main.MainViewModel;
import com.kanci.utils.AppSession;

public abstract class AuthActivity<ActivityMainBinding, MainViewModel> extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!AppSession.isLogin()) {
            startActivity(LoginActivity.newIntent(this));
            return;
        }
        setup();
    }
}
