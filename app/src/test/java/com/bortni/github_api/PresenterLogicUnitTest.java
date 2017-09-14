package com.bortni.github_api;

import com.bortni.github_api.ui.MainContract;
import com.bortni.github_api.ui.MainPresenter;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * Created by andrewbortnichuk on 14/09/2017.
 */

public class PresenterLogicUnitTest {

    @Mock
    MainContract.View view;

    private MainPresenter mainPresenter;

    @Before
    public void setupPresenter() {
        MockitoAnnotations.initMocks(this);
        mainPresenter = new MainPresenter();
        mainPresenter.takeView(view);
    }

    @Test
    public void getDataForTheFirstPage() {

    }

    @Test
    public void getDataForTheNextPage() {

    }

    @Test
    public void getDataOnTheLastPage() {

    }

    @Test
    public void getDataOnTheScrollToBottom() {

    }
}
