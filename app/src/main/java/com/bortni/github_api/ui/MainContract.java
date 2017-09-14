package com.bortni.github_api.ui;


import com.bortni.BasePresenter;
import com.bortni.BaseView;
import com.bortni.github_api.model.GithubRepoModel;

import java.util.List;

/**
 * Created by andrewbortnichuk on 18/08/2017.
 */

public interface MainContract {
    interface View extends BaseView {

        void showLoadingBar(boolean b);

        void startBottomLoading(boolean isLoading);

        boolean isActive();

        void showData(List<GithubRepoModel> data, int i);

        void showError(Throwable throwable);
    }

    interface Presenter extends BasePresenter<View> {
        void getData(int page);
    }
}
