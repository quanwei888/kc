package com.kanci.ui.word.main;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.kanci.BR;
import com.kanci.R;
import com.kanci.data.model.db.BookWordDef;
import com.kanci.data.model.db.TaskWord;
import com.kanci.databinding.ActivityWordMainBinding;
import com.kanci.ui.base.BaseActivity;

import javax.inject.Inject;

public class MainActivity extends BaseActivity implements MainViewModel.View {
    @Inject
    public MainViewModel vm;
    public ActivityWordMainBinding binding;
    WordHeadFragment wordFragment;

    public static Intent newIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_word_main);
        binding.setVariable(BR.vm, vm);
        binding.executePendingBindings();
        setup();
    }

    protected void setup() {
        wordFragment = new WordHeadFragment();
        vm.loadData();
    }

    @Override
    public void showWord(TaskWord taskWord, BookWordDef wordDef, int fragmentType) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        wordFragment.setTaskWord(taskWord);
        wordFragment.setWordDef(wordDef);
        fragmentTransaction.replace(R.id.fragmentView, wordFragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onComplete() {

    }

    @Override
    public void showTip() {
        wordFragment.showTip();
    }
}
