package com.bortni.github_api.di.modules;


import com.bortni.github_api.ui.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by andrewbortnichuk on 19/08/2017.
 */
@Module
public abstract class ActivityModule {
    @ContributesAndroidInjector(modules = MainActivityModule.class)
    abstract MainActivity mainActivity();
}
