package com.bortni.github_api.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.bortni.github_api.R;

public class MainActivity extends AppCompatActivity implements MainContract.View {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void showLoadingBar(boolean b) {

    }

    @Override
    public boolean isActive() {
        return false;
    }

    @Override
    public void showData() {

    }

    @Override
    public void showError(Throwable throwable) {

    }
}
