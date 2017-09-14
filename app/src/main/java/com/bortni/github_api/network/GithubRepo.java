package com.bortni.github_api.network;

import android.content.Context;

import com.bortni.github_api.model.GithubRepoModel;
import com.bortni.github_api.network.api.GithubApi;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by andrewbortnichuk on 14/09/2017.
 */

public class GithubRepo {
    private final Context context;

    public GithubRepo(Context context) {
        this.context = context;
    }

    public void getGithubData(int page, OnNetworkResponse<List<GithubRepoModel>> onNetworkResponse) {
        RetrofitFactory.newRetrofit(context)
                .create(GithubApi.class)
                .getRepoModel(page)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onNetworkResponse::success, onNetworkResponse::error);
    }
}
