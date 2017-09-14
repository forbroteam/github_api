package com.bortni.github_api.di.modules;

import android.app.Application;

import com.bortni.github_api.network.repo.GithubRepo;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by andrewbortnichuk on 18/08/2017.
 */
@Module
public class NetworkModule {

    @Singleton
    @Provides
    GithubRepo provideGithubRepo(Application context) {
        return new GithubRepo(context);
    }
}
