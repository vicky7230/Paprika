package com.vicky7230.eatit.ui.splash;

import com.vicky7230.eatit.ui.base.MvpPresenter;

public interface SplashMvpPresenter<V extends SplashMvpView> extends MvpPresenter<V> {

    void startCounting();
}
