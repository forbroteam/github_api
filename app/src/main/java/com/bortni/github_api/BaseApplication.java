package com.bortni.github_api;


import android.util.Log;

import com.bortni.github_api.di.AppComponent;
import com.bortni.github_api.di.DaggerAppComponent;
import com.bortni.github_api.network.GithubRepo;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;
import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by andrewbortnichuk on 18/08/2017.
 */

public class BaseApplication extends DaggerApplication {

    //doing it once in application file to have and share instance to our UI

    @Inject
    GithubRepo githubRepo;

    @Override
    public void onCreate() {
        super.onCreate();
        RealmConfiguration config = new RealmConfiguration.Builder().name("test.realm").build();
        Realm.setDefaultConfiguration(config);
    }

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        Realm.init(this);
        AppComponent appComponent = DaggerAppComponent.builder().application(this).build();
        appComponent.inject(this);
        return appComponent;
    }
}
