package com.kanci.ui.task.card;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.kanci.R;
import com.kanci.databinding.ActivityTaskCardBinding;
import com.kanci.ui.base.BaseActivity;
import com.kanci.ui.base.BaseViewModel;
import com.kanci.ui.home.HomeActivity;

public class CardActivity extends BaseActivity<ActivityTaskCardBinding, CardViewModel> {
    @Override
    public int getLayoutId() {
        return R.layout.activity_task_card;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int bookId = getIntent().getIntExtra("bookId", 0);
        VM().doLoadTaskWordList(bookId);
    }

    public void onLoadTaskWordList() {
        VM().doNextWord();
    }

    public void onSetWord() {
        VM().doNextWord();
    }

    public void onCommitAnswer(View view) {
    }

    public void onDoneWord(View view) {
        VM().doSetWordTag(VM().word.get(), 1);
    }

    public void onComplateTaskPlan() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }
}
