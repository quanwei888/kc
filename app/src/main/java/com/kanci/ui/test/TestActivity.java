package com.kanci.ui.test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.kanci.R;

import javax.inject.Inject;

public class TestActivity extends BaseActivity {
    @Inject
    public TestViewModel vm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_test);
    }
}
