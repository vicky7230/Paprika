package com.vicky7230.eatit.ui.search;

import dagger.Module;
import dagger.Provides;

@Module
public class SearchActivityModule {

    @Provides
    SearchMvpPresenter<SearchMvpView> provideSearchMvpPresenter(SearchPresenter<SearchMvpView> presenter) {
        return presenter;
    }
}
