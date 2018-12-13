package com.kanci.ui.word.main;

import android.databinding.ObservableField;

import com.kanci.data.model.db.BookWordDef;
import com.kanci.data.model.db.TaskWord;
import com.kanci.ui.base.BaseViewModel;

import javax.inject.Inject;

public class WordHeadViewModel extends BaseViewModel {
    @Inject
    public WordHeadViewModel() {
    }

    public ObservableField<TaskWord> taskWord = new ObservableField<>();
    public ObservableField<BookWordDef> wordDef = new ObservableField<>();
}
