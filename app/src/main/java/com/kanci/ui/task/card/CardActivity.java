package com.kanci.ui.task.card;

import android.os.Bundle;
import android.view.View;

import com.kanci.ui.base.BaseActivity;
import com.kanci.ui.base.BaseViewModel;

public class CardActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayoutId() {
        return 0;
    }

    @Override
    public int getBindingId() {
        return 0;
    }

    @Override
    public BaseViewModel createViewModel() {
        return null;
    }

    @Override
    public CardViewModel VM() {
        return VM();
    }

    public void onLoadTaskWordList() {
    }

    public void onSetWord() {
    }

    public void onCommitAnswer(View view) {
    }

    public void onDoneWord() {
    }

    public void onComplateTaskPlan() {
    }
}
