package com.bortni.github_api.network.api;

import com.bortni.github_api.model.GithubRepoModel;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by andrewbortnichuk on 14/09/2017.
 */

public interface GithubRepoApi {
    @GET("users/JakeWharton/repos?per_page=15")
    Observable<List<GithubRepoModel>> getRepoModel(@Query("page") int page);
}
