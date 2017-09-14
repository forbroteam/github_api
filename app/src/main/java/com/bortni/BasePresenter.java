package com.bortni;

/**
 * Created by andrewbortnichuk on 20/07/2017.
 */

public interface BasePresenter<T> {
    void takeView(T view);

    void deleteView();
}
