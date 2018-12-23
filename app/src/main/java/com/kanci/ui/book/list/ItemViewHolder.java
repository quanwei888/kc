package com.kanci.ui.book.list;

import android.databinding.ViewDataBinding;
import android.view.View;

import com.kanci.data.model.bean.Book;
import com.kanci.ui.base.BaseAdapter;
import com.kanci.ui.base.BaseViewHolder;
import com.kanci.ui.base.BaseViewModel;

import java.util.List;

/**
 * Created by qw on 18-12-23.
 */

public class ItemViewHolder extends BaseViewHolder<Book> {
    public ItemViewHolder(ViewDataBinding binding, BaseViewModel.BaseView view, BaseAdapter<Book> adapter) {
        super(binding, view, adapter);
    }

    @Override
    public void onBind(List<Book> data, int pos) {
        Book book = (Book) data.get(pos);
        VM().book.set(book);
        VM().isFirst.set(false);
        VM().isLast.set(false);
        VM().isSecFirst.set(false);
        if (pos == 0) {
            VM().isFirst.set(true);
        }
        if (pos == data.size() - 1) {
            VM().isLast.set(true);
        }

        if (pos > 0) {
            Book lastBook = (Book) data.get(pos - 1);
            if (!lastBook.tag.equals(book.tag)) {
                VM().isSecFirst.set(true);
            }
        } else {
            VM().isSecFirst.set(true);
        }
        getBinding().getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBookItemClick(book.bookId);
            }
        });
    }

    @Override
    public ItemViewModel VM() {
        return (ItemViewModel) super.VM();
    }

    @Override
    public BaseViewModel createViewModel() {
        return new ItemViewModel(this);
    }

    public void onBookItemClick(int bookId) {
        ((ListActivity) RV()).onBookItemClick(bookId);
    }
}
