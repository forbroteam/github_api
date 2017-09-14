package com.bortni.github_api.ui;

import android.os.Bundle;
import android.util.Log;

import com.bortni.github_api.R;
import com.bortni.github_api.model.GithubRepoModel;

import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public class MainActivity extends DaggerAppCompatActivity implements MainContract.View {
    private static final String TAG = "TAG_MAIN";

    @Inject
    MainPresenter mainPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, mainPresenter.toString());
    }

    @Override
    public void showLoadingBar(boolean b) {

    }

    @Override
    public void startBottomLoading(boolean isLoading) {

    }

    @Override
    public boolean isActive() {
        return false;
    }

    @Override
    public void showData(List<GithubRepoModel> data, int i) {

    }

    @Override
    public void showError(Throwable throwable) {

    }

    @Override
    public void lastPage(boolean b) {

    }
}
