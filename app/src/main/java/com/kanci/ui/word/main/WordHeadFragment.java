package com.kanci.ui.word.main;

import android.app.Fragment;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.kanci.BR;
import com.kanci.R;
import com.kanci.data.model.db.BookWordDef;
import com.kanci.data.model.db.TaskWord;
import com.kanci.databinding.FragmentWordHeadBinding;
import com.kanci.di.DaggerAppComponent;
import com.kanci.utils.AppGestureListener;
import com.kanci.utils.AppTouchListener;

import javax.inject.Inject;

public class WordHeadFragment extends Fragment {
    @Inject
    public WordHeadViewModel vm;
    public FragmentWordHeadBinding binding;
    private GestureDetectorCompat detector;


    public WordHeadFragment() {
        DaggerAppComponent.builder().build().inject(this);
    }

    public void setTaskWord(TaskWord taskWord) {
        vm.taskWord.set(taskWord);
    }

    public void setWordDef(BookWordDef wordDef) {
        vm.wordDef.set(wordDef);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_word_head, container, false);
        binding.setVariable(BR.vm, vm);
        View view = binding.getRoot();
        setup();
        return view;
    }

    public void setup() {
        View view = binding.getRoot();
        detector = new GestureDetectorCompat(getActivity(), new AppGestureListener() {

            @Override
            public void onUp() {
                showTip();
                System.out.println("onUp");
            }

            @Override
            public void onDown() {
                hideTip();
                System.out.println("onDown");
            }
        });
        view.setOnTouchListener(new AppTouchListener(detector));
    }

    public void showTip() {
        LinearLayout layout = binding.tipView;
        for (int i = 0; i < layout.getChildCount(); i++) {
            View child = layout.getChildAt(i);
            if (child.getVisibility() == View.INVISIBLE) {
                child.setVisibility(View.VISIBLE);
                break;
            }
            Log.i("tag", String.format("%d %d %d %d", child.getLeft(), child.getTop(), child.getRight(), child.getBottom()));
        }
    }

    public void hideTip() {
        LinearLayout layout = binding.tipView;
        for (int i = layout.getChildCount() - 1; i >= 0; i--) {
            View child = layout.getChildAt(i);
            if (child.getVisibility() == View.VISIBLE) {
                child.setVisibility(View.INVISIBLE);
                break;
            }
            Log.i("tag", String.format("%d %d %d %d", child.getLeft(), child.getTop(), child.getRight(), child.getBottom()));
        }
    }
}
