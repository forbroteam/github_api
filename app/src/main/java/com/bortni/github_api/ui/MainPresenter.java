package com.bortni.github_api.ui;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.bortni.github_api.model.GithubRepoModel;
import com.bortni.github_api.network.GithubRepo;
import com.bortni.github_api.network.OnNetworkResponse;

import java.util.List;

/**
 * Created by andrewbortnichuk on 14/09/2017.
 */

public class MainPresenter implements MainContract.Presenter {

    private final GithubRepo githubRepo;
    private int page = 1;
    private int startPage = 1;

    @Nullable
    private MainContract.View presenterView;

    public MainPresenter(GithubRepo _githubRepo) {
        this.githubRepo = _githubRepo;
    }

    @Override
    public void takeView(MainContract.View view) {
        presenterView = view;
    }

    @Override
    public void deleteView() {
        presenterView = null;
    }

    @Override
    public void getData(int _page) {
        if (presenterView != null) {
            if (_page > startPage) {
                presenterView.startBottomLoading(true);
            } else {
                page = startPage;
                presenterView.showLoadingBar(true);
            }
        }

        githubRepo.getGithubData(_page, new OnNetworkResponse<List<GithubRepoModel>>() {
            @Override
            public void success(@NonNull List<GithubRepoModel> githubRepoModels) {

                if (_page > startPage) {
                    presenterView.startBottomLoading(false);
                } else {
                    presenterView.showLoadingBar(false);
                }

                if (githubRepoModels.size() > 0) {
                    presenterView.showData(githubRepoModels, _page);
                }
            }

            @Override
            public void error(@Nullable Throwable throwable) {
                presenterView.showLoadingBar(false);
                presenterView.showError(throwable);
            }
        });
    }
}
