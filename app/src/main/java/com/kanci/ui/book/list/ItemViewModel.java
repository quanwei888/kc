package com.kanci.ui.book.list;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;

import com.kanci.data.model.bean.Book;
import com.kanci.ui.base.BaseViewModel;

/**
 * Created by qw on 18-12-23.
 */

public class ItemViewModel extends BaseViewModel {
    public ObservableField<Book> book = new ObservableField<>();
    public ObservableBoolean isFirst = new ObservableBoolean(false);
    public ObservableBoolean isLast = new ObservableBoolean(false);
    public ObservableBoolean isSecFirst = new ObservableBoolean(false);

}
