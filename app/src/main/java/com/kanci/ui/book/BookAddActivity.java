package com.kanci.ui.book;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.kanci.BR;
import com.kanci.R;
import com.kanci.data.AppDataManager;
import com.kanci.data.model.bean.Book;
import com.kanci.databinding.ActivityBookAddBinding;
import com.kanci.ui.base.BaseActivity;
import com.kanci.ui.MainActivity;

import io.reactivex.Maybe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class BookAddActivity extends BaseActivity {
    public ViewModel vm;
    public ActivityBookAddBinding binding;

    public static Intent newIntent(Context context) {
        return new Intent(context, BookAddActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vm = new ViewModel();
        binding = DataBindingUtil.setContentView(this, R.layout.activity_book_add);
        binding.setVariable(BR.vm, vm);
        binding.executePendingBindings();
        setup();
        binding.topbar.addLeftBackImageButton();
        binding.topbar.setTitle("添加单词书");
    }

    protected void setup() {
        Book book = (Book) getIntent().getSerializableExtra("book");
        vm.book = book;
        binding.setVariable(BR.vm, vm);
    }

    public class ViewModel {
        public AppDataManager dataManager = AppDataManager.instance(BookAddActivity.this);
        public Book book;


        public void addBook() {
            Maybe<Void> single = Maybe.create(emitter -> {
                int plan = Integer.parseInt(binding.planView.getText().toString());
                dataManager.addBook(book.bookId, plan);
                dataManager.cacheBookState();
                emitter.onComplete();
            });

            CompositeDisposable compositeDisposable = new CompositeDisposable();
            Disposable disposable = single.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            result -> {
                            },
                            error -> {
                                BookAddActivity.this.handleError(error);
                            },
                            () -> {
                                startActivity(MainActivity.newIntent(BookAddActivity.this));
                            }
                    );
            compositeDisposable.add(disposable);
        }

    }
}
