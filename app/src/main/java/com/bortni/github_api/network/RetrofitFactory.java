package com.bortni.github_api.network;

import android.content.Context;
import android.support.annotation.NonNull;

import com.bortni.github_api.BuildConfig;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by andrewbortnichuk on 14/09/2017.
 */

public class RetrofitFactory {
    private final static String githubUrl = "https://api.github.com/";

    @NonNull
    public static Retrofit newRetrofit(@NonNull final Context context) {
        return new Retrofit.Builder()
                .baseUrl(githubUrl)
                .client(newOkHttpClient(context))
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    @NonNull
    private static OkHttpClient newOkHttpClient(@NonNull final Context context) {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC);
        return new OkHttpClient.Builder()
                .cache(BuildConfig.DEBUG ? null : new Cache(context.getCacheDir(), 10 * 1024 * 1024)).addInterceptor(logging)
                .build();
    }
}
