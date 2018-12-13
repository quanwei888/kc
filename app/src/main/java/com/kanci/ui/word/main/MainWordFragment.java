package com.kanci.ui.word.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.GestureDetectorCompat;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.kanci.R;
import com.kanci.data.model.db.BookWordDef;
import com.kanci.data.model.db.TaskWord;
import com.kanci.utils.AppGestureListener;
import com.kanci.utils.AppTouchListener;

import javax.inject.Inject;

public class MainWordFragment extends Fragment {
    public MainWordViewModel vm;
    private GestureDetectorCompat detector;


    TaskWord taskWord;
    BookWordDef wordDef;

    public MainWordFragment() {
    }

    public static MainWordFragment newInstance(TaskWord taskWord, BookWordDef wordDef) {
        MainWordFragment fragment = new MainWordFragment();
        Bundle args = new Bundle();
        args.putSerializable("taskWord", taskWord);
        args.putSerializable("wordDef", wordDef);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            taskWord = (TaskWord) getArguments().getSerializable("taskWord");
            wordDef = (BookWordDef) getArguments().getSerializable("wordDef");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_word_main_word, container, false);
        detector = new GestureDetectorCompat(getActivity(), new AppGestureListener() {

            @Override
            public void onUp() {
                
            }

            @Override
            public void onDown() {

            }
        });
        view.setOnTouchListener(new AppTouchListener(detector));
        return view;
    }
}
