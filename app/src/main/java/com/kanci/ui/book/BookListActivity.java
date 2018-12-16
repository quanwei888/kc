package com.kanci.ui.book;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.kanci.BR;
import com.kanci.R;
import com.kanci.data.AppDataManager;
import com.kanci.data.model.bean.Book;
import com.kanci.databinding.ActivityBookListBinding;
import com.kanci.ui.base.BaseActivity;
import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView;
import com.qmuiteam.qmui.widget.grouplist.QMUIGroupListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class BookListActivity extends BaseActivity {
    public ViewModel vm;
    public ActivityBookListBinding binding;

    public static Intent newIntent(Context context) {
        return new Intent(context, BookListActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vm = new ViewModel();
        binding = DataBindingUtil.setContentView(this, R.layout.activity_book_list);
        binding.setVariable(BR.vm, vm);
        binding.executePendingBindings();
        setup();
        binding.topbar.addLeftBackImageButton();
        binding.topbar.setTitle("单词书");
    }

    protected void setup() {
        vm.loadData();
    }

    public void showBookList(Set<String> tags, Map<String, List<Book>> books) {
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v instanceof QMUICommonListItemView) {
                    Book book = (Book) v.getTag();
                    Intent intent = BookAddActivity.newIntent(BookListActivity.this);
                    intent.putExtra("book", book);
                    startActivity(intent);
                }
            }
        };
        for (String tag : tags) {
            QMUIGroupListView.Section section = QMUIGroupListView.newSection(getContext()).setTitle(tag);
            for (Book book : books.get(tag)) {
                QMUICommonListItemView itemView = binding.bookListView.createItemView(book.bookName);
                itemView.setOrientation(QMUICommonListItemView.VERTICAL);
                itemView.setDetailText("单词数：" + book.wordCount);
                itemView.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON);
                itemView.setTag(book);
                ArrayList<View> btns = new ArrayList<>();
                section.addItemView(itemView, onClickListener);
            }
            section.addTo(binding.bookListView);
        }

    }

    public class ViewModel {
        public AppDataManager dataManager = AppDataManager.instance(BookListActivity.this);
        public Map<String, List<Book>> books = new HashMap<>();
        public Set<String> tags = new TreeSet<>();

        /**
         * 加载单词书列表
         */
        public void loadData() {
            Single<List<Book>> single = Single.create(emitter -> {
                List<Book> bookList = dataManager.getBookList();
                emitter.onSuccess(bookList);
            });

            CompositeDisposable compositeDisposable = new CompositeDisposable();
            Disposable disposable = single.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            result -> {
                                //Book List
                                for (Book book : result) {
                                    tags.add(book.tag);
                                    if (!books.containsKey(book.tag)) {
                                        books.put(book.tag, new ArrayList<>());
                                    }
                                    books.get(book.tag).add(book);
                                }
                                BookListActivity.this.showBookList(tags, books);
                            },
                            error -> {
                                BookListActivity.this.handleError(error);
                            }
                    );
            compositeDisposable.add(disposable);
        }
    }
}
