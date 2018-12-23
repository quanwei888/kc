package com.kanci.ui.book.list;

import android.databinding.ViewDataBinding;

import com.kanci.R;
import com.kanci.data.model.bean.Book;
import com.kanci.ui.base.BaseAdapter;
import com.kanci.ui.base.BaseViewHolder;
import com.kanci.ui.base.BaseViewModel;

public class Adapter extends BaseAdapter<Book> {
    public Adapter(BaseViewModel.BaseView view) {
        super(view);
    }

    @Override
    public BaseViewHolder createViewHolder(ViewDataBinding binding, BaseViewModel.BaseView view) {
        return new ItemViewHolder(binding, view, this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_book;
    }

}
