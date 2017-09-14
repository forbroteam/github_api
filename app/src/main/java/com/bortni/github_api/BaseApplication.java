package com.bortni.github_api;


import com.bortni.github_api.di.AppComponent;
import com.bortni.github_api.di.DaggerAppComponent;
import com.bortni.github_api.network.GithubRepo;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;

/**
 * Created by andrewbortnichuk on 18/08/2017.
 */

public class BaseApplication extends DaggerApplication {

    //doing it once in application file to have and share instance to our UI

    @Inject
    GithubRepo githubRepo;

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        AppComponent appComponent = DaggerAppComponent.builder().application(this).build();
        appComponent.inject(this);
        return appComponent;
    }
}
