package com.bortni.github_api.network;

import android.content.Context;

import com.bortni.github_api.model.GithubRepoModel;
import com.bortni.github_api.network.api.GithubRepoApi;

import java.util.List;

import javax.inject.Singleton;

import io.realm.Realm;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by andrewbortnichuk on 14/09/2017.
 */
@Singleton
public class GithubRepo {
    private final Context context;
    private Realm realm;

    public GithubRepo(Context context) {
        this.context = context;
    }

    public void getGithubData(int page, OnNetworkResponse<List<GithubRepoModel>> onNetworkResponse) {
        realm = Realm.getDefaultInstance();
        RetrofitFactory.newRetrofit(context)
                .create(GithubRepoApi.class)
                .getRepoModel(page)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(githubRepoModels -> {
                            realm.executeTransaction(realmParam -> {
                                for (GithubRepoModel githubRepoModel : githubRepoModels) {
                                    realmParam.copyToRealmOrUpdate(githubRepoModel);
                                }
                            });
                            realm.close();
                            onNetworkResponse.success(githubRepoModels);

                        },
                        onNetworkResponse::error);
    }
}
