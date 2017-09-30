package com.vicky7230.eatit.ui.splash;

import dagger.Module;
import dagger.Provides;

@Module
public class SplashActivityModule {

    @Provides
    SplashMvpPresenter<SplashMvpView> provideSplashMvpPresenter(SplashPresenter<SplashMvpView> presenter) {
        return presenter;
    }
}
