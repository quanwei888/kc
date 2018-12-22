package com.kanci.ui.base;

public abstract class AuthActivity extends BaseActivity {
    public void handleError(Throwable e) {
        //未登录处理
        super.handleError(e);
    }
}