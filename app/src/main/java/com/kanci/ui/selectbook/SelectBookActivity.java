package com.kanci.ui.selectbook;

import android.content.Context;
import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.LinearLayoutManager;

import com.kanci.BR;
import com.kanci.R;
import com.kanci.databinding.ActivitySelectBookBinding;
import com.kanci.ui.base.BaseViewModel;
import com.kanci.ui.base.NonAuthActivity;
import com.malinskiy.superrecyclerview.OnMoreListener;

public class SelectBookActivity extends NonAuthActivity<ActivitySelectBookBinding, SelectBookViewModel> {
    public SelectBookViewModel vm;
    public ActivitySelectBookBinding binding;

    @Override
    protected BaseViewModel getViewModel() {
        return vm = (vm != null ? vm : new SelectBookViewModel());
    }

    @Override
    protected int getBindingVariable() {
        return BR.vm;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_select_book;
    }

    @Override
    public ViewDataBinding getViewBinding() {
        return binding;
    }

    @Override
    public void setViewBinding(ViewDataBinding binding) {
        this.binding = (ActivitySelectBookBinding) binding;
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, SelectBookActivity.class);
    }

    @Override
    protected void setup() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.bookListView.setLayoutManager(layoutManager);
        binding.bookListView.setRefreshListener(() -> {
            vm.loadBookList();
        });
        binding.bookListView.setupMoreListener(new OnMoreListener() {

            @Override
            public void onMoreAsked(int overallItemsCount, int itemsBeforeMore, int maxLastVisiblePosition) {
                vm.loadBookList();
                //binding.bookListView.removeMoreListener();
            }
        }, 1);
        //binding.bookListView.setAdapter(vm.adapter);
        vm.loadBookList();
    }
}
