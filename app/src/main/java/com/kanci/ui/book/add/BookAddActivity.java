package com.kanci.ui.book.add;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.kanci.BR;
import com.kanci.R;
import com.kanci.data.model.bean.Book;
import com.kanci.databinding.ActivityBookAddBinding;
import com.kanci.di.AppModule;
import com.kanci.di.DaggerAppComponent;
import com.kanci.ui.ActivityMgr;
import com.kanci.ui.BaseActivity;
import com.kanci.ui.book.BookListAdapter;

import java.util.Map;

import javax.inject.Inject;

public class BookAddActivity extends BaseActivity implements BookAddViewModel.View {
    @Inject
    public BookAddViewModel vm;
    public ActivityBookAddBinding binding;

    public static Intent newIntent(Context context) {
        return new Intent(context, BookAddActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        DaggerAppComponent.builder().appModule(new AppModule(this)).build().inject(this);
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_book_add);
        binding.setVariable(BR.vm, vm);
        binding.executePendingBindings();
        setup();
    }

    protected void setup() {
        vm.loadData();
    }

    @Override
    public void showBookList(Map<String, BookListAdapter> adapters) {
        for (String key : adapters.keySet()) {
            RecyclerView lv = new RecyclerView(this);
            RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false);
            lv.setLayoutManager(layoutManager);
            lv.setAdapter(adapters.get(key));
            adapters.get(key).setOnItemClickListener((v) -> {
                        Book book = (Book) v.getTag();
                        ActivityMgr.gotoBookPlan(this, book);
                    }
            );

            View tv = LayoutInflater.from(this).inflate(R.layout.view_book_tag, null);
            ((TextView) tv.findViewById(R.id.tag)).setText(key);
            binding.bookGroup.addView(tv);
            binding.bookGroup.addView(lv);
        }
    }
}
