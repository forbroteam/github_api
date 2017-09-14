package com.bortni.github_api.ui;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.bortni.github_api.model.GithubRepoModel;
import com.bortni.github_api.network.GithubRepo;
import com.bortni.github_api.network.OnNetworkResponse;

import java.util.List;

import javax.inject.Inject;

import io.realm.Realm;
import io.realm.RealmQuery;

/**
 * Created by andrewbortnichuk on 14/09/2017.
 */

public class MainPresenter implements MainContract.Presenter {

    private final GithubRepo githubRepo;
    private int page = 1;
    private int startPage = 1;

    @Nullable
    private MainContract.View presenterView;
    private boolean isLocal = false;

    @Inject
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
                isLocal(false);
                if (_page > startPage) {
                    presenterView.startBottomLoading(false);
                } else {
                    presenterView.showLoadingBar(false);
                }

                if (githubRepoModels.size() > 0) {
                    presenterView.lastPage(false);
                    presenterView.showData(githubRepoModels, _page);
                } else {
                    presenterView.lastPage(true);
                }
            }

            @Override
            public void error(@Nullable Throwable throwable) {
                if (_page > startPage) {
                    presenterView.startBottomLoading(false);
                } else {
                    presenterView.showLoadingBar(false);
                }

                if (getLocalGithubRepoModels() != null &&
                        getLocalGithubRepoModels().size() > 0) {
                    isLocal(true);
                    presenterView.showLocalData(getLocalGithubRepoModels());
                } else {
                    page = 0;
                    presenterView.showError(throwable);
                }
            }
        });
    }

    @Override
    public void getDataOnBottomList(boolean isBottom) {
        if (isBottom && !isLocal) {
            page++;
            getData(page);
        }
    }

    @Override
    public void isLocal(boolean _isLocal) {
        this.isLocal = _isLocal;
    }

    @Override
    public List<GithubRepoModel> getLocalGithubRepoModels() {
        if (Realm.getDefaultInstance() != null) {
            return RealmQuery.createQuery(Realm.getDefaultInstance(),
                    GithubRepoModel.class).findAll();
        } else return null;
    }
}
