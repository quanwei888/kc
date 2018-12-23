package com.kanci.ui.book.wordlist.fragment;

import android.databinding.ViewDataBinding;
import android.view.View;

import com.kanci.data.model.db.Word;
import com.kanci.databinding.ItemWordBinding;
import com.kanci.ui.base.BaseAdapter;
import com.kanci.ui.base.BaseViewHolder;
import com.kanci.ui.base.BaseViewModel;

import java.util.List;

/**
 * Created by qw on 18-12-23.
 */

public class ItemViewHolder extends BaseViewHolder<Word> {
    public ItemViewHolder(ViewDataBinding binding, BaseViewModel.BaseView rootView, BaseAdapter<Word> adapter) {
        super(binding, rootView, adapter);
    }

    @Override
    public void onBind(List<Word> data, int pos) {
        Word word = (Word) data.get(pos);
        VM().word.set(word);
        VM().isFirst.set(false);
        VM().isLast.set(false);
        if (pos == 0) {
            VM().isFirst.set(true);
        }
        if (pos == data.size() - 1) {
            VM().isLast.set(true);
        }

        ((ItemWordBinding) getBinding()).doneView.setTag(word);
    }

    @Override
    public BaseViewModel createViewModel() {
        return new ItemViewModel(this);
    }

    @Override
    public ItemViewModel VM() {
        return (ItemViewModel) super.VM();
    }

    @Override
    public ListFragment RV() {
        return (ListFragment) super.RV();
    }

    public void onDoneWordEvent(View v) {
        Word word = (Word) v.getTag();
        VM().doSetWordTag(word, 1);
    }

    public void onCancelDoneEvent(View v) {
        Word word = (Word) v.getTag();
        VM().doSetWordTag(word, 0);
    }

    public void onSetWord(Word word) {
        getAdapter().remove(word);
    }

}
