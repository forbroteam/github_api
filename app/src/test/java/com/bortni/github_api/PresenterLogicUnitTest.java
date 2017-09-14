package com.bortni.github_api;

import com.bortni.github_api.model.GithubRepoModel;
import com.bortni.github_api.network.GithubRepo;
import com.bortni.github_api.network.OnNetworkResponse;
import com.bortni.github_api.ui.MainContract;
import com.bortni.github_api.ui.MainPresenter;
import com.bortni.github_api.utils.TestUtils;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by andrewbortnichuk on 14/09/2017.
 */

public class PresenterLogicUnitTest {
    @Mock
    GithubRepo githubRepo;

    @Mock
    MainContract.View view;

    @Captor
    private ArgumentCaptor<OnNetworkResponse> mLoadCallbackCaptor;

    private MainPresenter mainPresenter;

    private List<GithubRepoModel> data;

    @Before
    public void setupPresenter() {
        MockitoAnnotations.initMocks(this);
        mainPresenter = new MainPresenter(githubRepo);
        mainPresenter.takeView(view);
        data = new ArrayList<>();

        when(view.isActive()).thenReturn(true);
    }

    @Test
    public void getDataForTheFirstPage() {
        mainPresenter.getData(1);

        verify(view, times(1)).showLoadingBar(true);

        verify(githubRepo, times(1)).getGithubData(eq(1), mLoadCallbackCaptor.capture());

        fillData();

        mLoadCallbackCaptor.getValue().success(data);

        verify(view, times(1)).showLoadingBar(false);

        verify(view, times(1)).showData(data, 1);
    }

    @Test
    public void getDataForTheNextPage() {
        mainPresenter.getData(2);

        verify(view, times(1)).startBottomLoading(true);

        verify(githubRepo, times(1)).getGithubData(eq(2), mLoadCallbackCaptor.capture());

        fillData();

        mLoadCallbackCaptor.getValue().success(data);

        verify(view, times(1)).startBottomLoading(false);

        verify(view, times(1)).showData(data, 2);
    }


    @Test
    public void getDataOnTheScrollToBottom() {
        mainPresenter.getDataOnBottomList(true);

        verify(view, times(1)).startBottomLoading(true);

        verify(githubRepo, times(1)).getGithubData(eq(2), mLoadCallbackCaptor.capture());

        fillData();

        mLoadCallbackCaptor.getValue().success(data);

        verify(view, times(1)).startBottomLoading(false);
        verify(view, times(1)).lastPage(false);

        verify(view, times(1)).showData(data, 2);
    }

    @Test
    public void getDataOnTheLastPage() {
        mainPresenter.getDataOnBottomList(true);

        verify(view, times(1)).startBottomLoading(true);

        verify(githubRepo, times(1)).getGithubData(eq(2), mLoadCallbackCaptor.capture());

        mLoadCallbackCaptor.getValue().success(new ArrayList<>());

        verify(view, times(1)).startBottomLoading(false);
        verify(view, times(1)).lastPage(true);
    }

    private void fillData() {
        TestUtils.fillGithubRepoModel();
        data.add(TestUtils.getGithubRepoModel());
        data.add(TestUtils.getGithubRepoModel());
        data.add(TestUtils.getGithubRepoModel());

    }
}
