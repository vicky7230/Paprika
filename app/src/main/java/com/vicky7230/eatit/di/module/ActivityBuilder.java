package com.vicky7230.eatit.di.module;

import com.vicky7230.eatit.ui.home.FragmentProvider;
import com.vicky7230.eatit.ui.home.HomeActivity;
import com.vicky7230.eatit.ui.home.HomeActivityModule;
import com.vicky7230.eatit.ui.search.SearchActivity;
import com.vicky7230.eatit.ui.search.SearchActivityModule;
import com.vicky7230.eatit.ui.splash.SplashActivity;
import com.vicky7230.eatit.ui.splash.SplashActivityModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by vipin on 17/9/17.
 */

@Module
public abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = {HomeActivityModule.class, FragmentProvider.class})
    abstract HomeActivity bindHomeActivity();

    @ContributesAndroidInjector(modules = SearchActivityModule.class)
    abstract SearchActivity bindSearchActivity();

    @ContributesAndroidInjector(modules = SplashActivityModule.class)
    abstract SplashActivity bindSplashActivity();

}
