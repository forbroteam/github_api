package com.bortni.github_api.di;

import android.app.Application;

import com.bortni.github_api.BaseApplication;
import com.bortni.github_api.di.modules.ActivityModule;
import com.bortni.github_api.di.modules.ApplicationModule;
import com.bortni.github_api.di.modules.NetworkModule;
import com.bortni.github_api.network.repo.GithubRepo;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;
import dagger.android.support.AndroidSupportInjectionModule;

/**
 * Created by andrewbortnichuk on 18/08/2017.
 */
@Singleton
@Component(modules = {NetworkModule.class,
        ApplicationModule.class,
        ActivityModule.class,
        AndroidSupportInjectionModule.class})

public interface AppComponent extends AndroidInjector<DaggerApplication> {

    void inject(BaseApplication application);

    GithubRepo getGithubRepo();

    @Override
    void inject(DaggerApplication instance);

    // Gives us syntactic sugar. we can then do DaggerAppComponent.builder().application(this).build().inject(this);
    // never having to instantiate any modules or say which module we are passing the application to.
    // Application will just be provided into our app graph now.
    @Component.Builder
    interface Builder {

        @BindsInstance
        AppComponent.Builder application(Application application);

        AppComponent build();
    }
}
