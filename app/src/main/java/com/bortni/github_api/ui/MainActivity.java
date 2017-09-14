package com.bortni.github_api.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.bortni.github_api.R;
import com.bortni.github_api.model.GithubRepoModel;

import java.util.List;

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
