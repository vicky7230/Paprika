package com.vicky7230.eatit.ui.home;

import dagger.Module;
import dagger.Provides;

/**
 * Created by vipin on 17/9/17.
 */

@Module
public class HomeActivityModule {

    @Provides
    ViewPagerAdapter provideViewPagerAdapter(HomeActivity homeActivity) {
        return new ViewPagerAdapter(homeActivity.getSupportFragmentManager());
    }

    @Provides
    HomeMvpPresenter<HomeMvpView> provideHomeMvpPresenter(HomePresenter<HomeMvpView> presenter) {
        return presenter;
    }
}
