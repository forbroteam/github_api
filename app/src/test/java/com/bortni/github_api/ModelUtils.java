package com.bortni.github_api;


import com.bortni.github_api.model.GithubRepoModel;

/**
 * Created by andrewbortnichuk on 12/09/2017.
 */

public class ModelUtils {
    private static GithubRepoModel githubRepoModel = new GithubRepoModel();

    public static GithubRepoModel getGithubRepoModel() {
        return githubRepoModel;
    }

    public static void fillGithubRepoModel() {
        githubRepoModel.setId(1);
        githubRepoModel.setName("name");
        githubRepoModel.setFullName("full_name");
        githubRepoModel.setHtmlUrl("url");
    }

    public static void cleanGithubRepoModel() {
        githubRepoModel = null;
    }
}
