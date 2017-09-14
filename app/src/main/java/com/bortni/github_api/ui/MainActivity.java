package com.bortni.github_api.ui;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.VisibleForTesting;
import android.support.design.widget.Snackbar;
import android.support.test.espresso.IdlingResource;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.bortni.github_api.R;
import com.bortni.github_api.model.GithubRepoModel;
import com.bortni.github_api.ui.adapter.RecyclerPaginationAdapter;
import com.bortni.github_api.ui.listener.PaginationScrollListener;
import com.bortni.github_api.utils.EspressoIdlingResource;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.DaggerAppCompatActivity;

public class MainActivity extends DaggerAppCompatActivity implements MainContract.View {
    private static final String TAG = "TAG_MAIN";
    @BindView(R.id.recycler_view)
    public
    RecyclerView recyclerView;
    @Inject
    MainPresenter mainPresenter;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefreshLayout;


    private boolean isActivityDestroyed = false;
    private int startPage = 1;

    private RecyclerPaginationAdapter recyclerPaginationAdapter;
    private boolean isLoading = false;
    private boolean isLastPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addOnScrollListener(new PaginationScrollListener(linearLayoutManager) {
            @Override
            protected void onListBottom() {
                //list behaviour thread separation and improvments
                recyclerView.post(() -> mainPresenter.getDataOnBottomList(!isLastPage));
            }

            @Override
            public boolean isLastPage() {
                return isLastPage;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });


        recyclerPaginationAdapter = new RecyclerPaginationAdapter();
        recyclerView.setAdapter(recyclerPaginationAdapter);
        swipeRefreshLayout.setOnRefreshListener(() -> mainPresenter.getData(startPage));

    }

    @Override
    protected void onResume() {
        super.onResume();
        mainPresenter.takeView(this);
        mainPresenter.getData(startPage);
    }

    @Override
    protected void onDestroy() {
        isActivityDestroyed = true;
        mainPresenter.deleteView();
        super.onDestroy();
    }

    @Override
    public void showLoadingBar(boolean show) {
        swipeRefreshLayout.setRefreshing(show);
    }

    @Override
    public void startBottomLoading(boolean _isLoading) {
        this.isLoading = _isLoading;

        if (isLoading) {
            recyclerPaginationAdapter.addLoadingFooter();
        } else {
            recyclerPaginationAdapter.removeLoadingFooter();
        }
    }

    @Override
    public boolean isActive() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return isDestroyed();
        } else return isActivityDestroyed;
    }

    @Override
    public void showLocalData(List<GithubRepoModel> githubRepoModels) {
        if (swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
        }
        Snackbar snackbar = Snackbar.make(recyclerView, R.string.local_data_show_text, Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction(R.string.snackbar_dismiss, view -> snackbar.dismiss()).setActionTextColor(Color.YELLOW);
        snackbar.show();
        recyclerPaginationAdapter.addAllLocal(githubRepoModels);
    }

    @Override
    public void showData(List<GithubRepoModel> githubRepoModels, int _page) {
        if (swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
        }
        if (_page > startPage) {
            recyclerPaginationAdapter.addAll(githubRepoModels);
        } else {
            recyclerPaginationAdapter.addAllFirstTime(githubRepoModels);
        }
    }

    @Override
    public void showError(Throwable throwable) {
        String errorMessage = getString(R.string.error_text) + throwable.getLocalizedMessage();
        Snackbar.make(recyclerView, errorMessage,
                Snackbar.LENGTH_INDEFINITE).setAction(R.string.retry_snackbar, view ->
                mainPresenter.getData(startPage)).setActionTextColor(Color.YELLOW).show();
    }

    @Override
    public void lastPage(boolean _isLastPage) {
        this.isLastPage = _isLastPage;
    }


    //purpose to make visible for Instrumentation testing
    @VisibleForTesting
    public IdlingResource getCountingIdlingResource() {
        return EspressoIdlingResource.getIdlingResource();
    }
}
