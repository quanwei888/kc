package com.kanci.ui.selectbook;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.GridView;

import com.kanci.BR;
import com.kanci.R;
import com.kanci.databinding.ActivitySelectBookBinding;
import com.kanci.di.AppModule;
import com.kanci.di.DaggerAppComponent;
import com.kanci.ui.BaseActivity;
import com.malinskiy.superrecyclerview.OnMoreListener;

import java.util.Map;

import javax.inject.Inject;

public class SelectBookActivity extends BaseActivity implements SelectBookViewModel.View {
    @Inject
    public SelectBookViewModel vm;
    public ActivitySelectBookBinding binding;

    public static Intent newIntent(Context context) {
        return new Intent(context, SelectBookActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        DaggerAppComponent.builder().appModule(new AppModule(this)).build().inject(this);
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_select_book);
        binding.setVariable(BR.vm, vm);
        binding.executePendingBindings();
        setup();
    }

    protected void setup() {
        vm.loadData();
    }

    @Override
    public void showBookList(Map<String, SelectBookAdapter> adapters) {
        for (String key : adapters.keySet()) {
            GridView gv = new GridView(this);
            gv.setAdapter(adapters.get(key));
            //View a = binding.bookGroup;
        }
    }
}
