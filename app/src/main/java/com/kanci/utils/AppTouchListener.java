package com.kanci.utils;

import android.support.v4.view.GestureDetectorCompat;
import android.view.MotionEvent;
import android.view.View;

public class AppTouchListener implements View.OnTouchListener {
    GestureDetectorCompat detector;

    public AppTouchListener(GestureDetectorCompat detector) {
        this.detector = detector;
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        return this.detector.onTouchEvent(event);
    }
}
