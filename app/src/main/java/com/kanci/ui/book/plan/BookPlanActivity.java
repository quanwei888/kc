package com.kanci.ui.book.plan;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.kanci.BR;
import com.kanci.R;
import com.kanci.data.model.bean.Book;
import com.kanci.databinding.ActivityBookPlanBinding;
import com.kanci.di.AppModule;
import com.kanci.di.DaggerAppComponent;
import com.kanci.ui.base.BaseActivity;

import javax.inject.Inject;

public class BookPlanActivity extends BaseActivity implements BookPlanViewModel.View {
    @Inject
    public BookPlanViewModel vm;
    public ActivityBookPlanBinding binding;

    public static Intent newIntent(Context context) {
        return new Intent(context, BookPlanActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        DaggerAppComponent.builder().appModule(new AppModule(this)).build().inject(this);
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_book_plan);
        binding.setVariable(BR.vm, vm);
        binding.executePendingBindings();
        setup();
    }

    protected void setup() {
        Book book = (Book) getIntent().getSerializableExtra("book");
        vm.book.set(book);
        vm.loadData();
    }
}
