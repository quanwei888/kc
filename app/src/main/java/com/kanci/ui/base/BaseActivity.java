/*
 *  Copyright (C) 2017 MINDORKS NEXTGEN protected LIMITED
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      https://mindorks.com/license/apache-v2
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License
 */

package com.kanci.ui.base;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.kanci.data.AppDataManager;

public abstract class BaseActivity<T extends ViewDataBinding, V extends BaseViewModel> extends AppCompatActivity {


    protected abstract BaseViewModel getViewModel();

    protected abstract int getBindingVariable();

    protected abstract int getLayoutId();

    public abstract ViewDataBinding getViewBinding();

    public abstract void setViewBinding(ViewDataBinding binding);

    protected abstract void setup();

    public void handleError(Throwable throwable) {
        Toast.makeText(this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        //初始化绑定
        setViewBinding(DataBindingUtil.setContentView(this, getLayoutId()));
        getViewBinding().setVariable(getBindingVariable(), getViewModel());
        getViewBinding().executePendingBindings();
        getViewModel().setView(this);
        getViewModel().setDataManager(AppDataManager.instance(this.getApplicationContext()));
    }
}

