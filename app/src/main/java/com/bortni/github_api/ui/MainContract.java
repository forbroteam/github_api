package com.bortni.github_api.ui;


import com.bortni.BasePresenter;
import com.bortni.BaseView;

/**
 * Created by andrewbortnichuk on 18/08/2017.
 */

public interface MainContract {
    interface View extends BaseView {
        void showLoadingBar(boolean b);

        boolean isActive();

        void showData();

        void showError(Throwable throwable);
    }

    interface Presenter extends BasePresenter<View> {
        void getData(int page);
    }
}
