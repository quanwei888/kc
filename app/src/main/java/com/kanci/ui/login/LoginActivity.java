package com.kanci.ui.login;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.kanci.R;
import com.kanci.ui.main.MainActivity;
import com.kanci.utils.AppSession;

public class LoginActivity extends AppCompatActivity {

    public static Intent newIntent(Context context) {
        return new Intent(context, LoginActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        AppSession.uid = 1;
        startActivity(MainActivity.newIntent(this));
    }

}
