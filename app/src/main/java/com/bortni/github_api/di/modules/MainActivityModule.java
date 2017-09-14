package com.bortni.github_api.di.modules;

import com.bortni.github_api.ui.MainContract;
import com.bortni.github_api.ui.MainPresenter;

import dagger.Binds;
import dagger.Module;

/**
 * Created by andrewbortnichuk on 19/08/2017.
 */
@Module
public abstract class MainActivityModule {
    @Binds
    abstract MainContract.Presenter dataPresenter(MainPresenter presenter);
}
