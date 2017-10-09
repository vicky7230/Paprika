package com.vicky7230.eatit.ui.home;

import android.content.Context;

import com.vicky7230.eatit.di.HomeActivityContext;

import dagger.Module;
import dagger.Provides;

/**
 * Created by vipin on 17/9/17.
 */

@Module
public class HomeActivityModule {

    @Provides
    @HomeActivityContext
    Context provideHomeActivityContext(HomeActivity homeActivity){
        return homeActivity;
    }

    @Provides
    ViewPagerAdapter provideViewPagerAdapter(HomeActivity homeActivity) {
        return new ViewPagerAdapter(homeActivity.getSupportFragmentManager());
    }

    @Provides
    HomeMvpPresenter<HomeMvpView> provideHomeMvpPresenter(HomePresenter<HomeMvpView> presenter) {
        return presenter;
    }
}
