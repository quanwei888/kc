package com.kanci.utils;

import android.view.GestureDetector;
import android.view.MotionEvent;

public abstract class AppGestureListener extends GestureDetector.SimpleOnGestureListener {
    private int minDistance = 100;
    private int minVelocity = 10;

    @Override
    public boolean onDown(MotionEvent e) {
        return true;
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float vx, float vy) {

        if (e1.getX() - e2.getX() > minDistance && Math.abs(vx) > minVelocity) {
            onLeft();
        } else if (e2.getX() - e1.getX() > minDistance && Math.abs(vx) > minVelocity) {
            onRight();
        } else if (e1.getY() - e2.getY() > minDistance && Math.abs(vy) > minVelocity) {
            onUp();
        } else if (e2.getY() - e1.getY() > minDistance && Math.abs(vy) > minVelocity) {
            onDown();
        }
        return true;
    }

    public void onUp() {

    }

    public void onDown() {

    }

    public void onLeft() {

    }

    public void onRight() {

    }

}
