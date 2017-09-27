package com.vicky7230.eatit.ui.search;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;

import com.vicky7230.eatit.data.network.model.recipes.Recipe;
import com.vicky7230.eatit.di.ApplicationContext;
import com.vicky7230.eatit.ui.home.recipes.RecipesAdapter;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;

@Module
public class SearchActivityModule {

    @Provides
    SearchMvpPresenter<SearchMvpView> provideSearchMvpPresenter(SearchPresenter<SearchMvpView> presenter) {
        return presenter;
    }

    @Provides
    LinearLayoutManager provideLinearLayoutManager(@ApplicationContext Context context) {
        return new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
    }

    @Provides
    SearchAdapter provideSearchAdapter() {
        return new SearchAdapter(new ArrayList<Recipe>());
    }

}
