package com.vicky7230.eatit.di.module;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.vicky7230.eatit.data.db.model.LikedRecipe;
import com.vicky7230.eatit.data.network.model.recipes.Recipe;
import com.vicky7230.eatit.di.ActivityContext;
import com.vicky7230.eatit.ui.home.HomeMvpPresenter;
import com.vicky7230.eatit.ui.home.HomeMvpView;
import com.vicky7230.eatit.ui.home.HomePresenter;
import com.vicky7230.eatit.ui.home.Imagga.ImaggaMvpPresenter;
import com.vicky7230.eatit.ui.home.Imagga.ImaggaMvpView;
import com.vicky7230.eatit.ui.home.Imagga.ImaggaPresenter;
import com.vicky7230.eatit.ui.home.ViewPagerAdapter;
import com.vicky7230.eatit.ui.home.likes.LikesAdapter;
import com.vicky7230.eatit.ui.home.likes.LikesMvpPresenter;
import com.vicky7230.eatit.ui.home.likes.LikesMvpView;
import com.vicky7230.eatit.ui.home.likes.LikesPresenter;
import com.vicky7230.eatit.ui.home.recipes.RecipesAdapter;
import com.vicky7230.eatit.ui.home.recipes.RecipesMvpPresenter;
import com.vicky7230.eatit.ui.home.recipes.RecipesMvpView;
import com.vicky7230.eatit.ui.home.recipes.RecipesPresenter;
import com.vicky7230.eatit.ui.search.SearchMvpPresenter;
import com.vicky7230.eatit.ui.search.SearchMvpView;
import com.vicky7230.eatit.ui.search.SearchPresenter;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by agrim on 2/7/17.
 */

@Module
public class ActivityModule {

    private AppCompatActivity appCompatActivity;

    public ActivityModule(AppCompatActivity appCompatActivity) {
        this.appCompatActivity = appCompatActivity;
    }

    @Provides
    AppCompatActivity provideActivity() {
        return appCompatActivity;
    }

    @Provides
    @ActivityContext
    Context provideContext() {
        return appCompatActivity;
    }

    @Provides
    CompositeDisposable provideCompositeDisposable() {
        return new CompositeDisposable();
    }

    @Provides
    ViewPagerAdapter provideViewPagerAdapter(AppCompatActivity appCompatActivity) {
        return new ViewPagerAdapter(appCompatActivity.getSupportFragmentManager());
    }

    @Provides
    RecipesAdapter provideRecipeAdapter() {
        return new RecipesAdapter(new ArrayList<Recipe>());
    }

    @Provides
    LikesAdapter provideFavouritesAdapter() {
        return new LikesAdapter(new ArrayList<LikedRecipe>());
    }

    @Provides
    HomeMvpPresenter<HomeMvpView> provideHomeMvpPresenter(HomePresenter<HomeMvpView> presenter) {
        return presenter;
    }

    @Provides
    SearchMvpPresenter<SearchMvpView> provideSearchMvpPresenter(SearchPresenter<SearchMvpView> presenter) {
        return presenter;
    }

    @Provides
    RecipesMvpPresenter<RecipesMvpView> provideRecipesMvpPresenter(RecipesPresenter<RecipesMvpView> presenter) {
        return presenter;
    }

    @Provides
    ImaggaMvpPresenter<ImaggaMvpView> provideRecipeOfDayMvpPresenter(ImaggaPresenter<ImaggaMvpView> presenter) {
        return presenter;
    }

    @Provides
    LikesMvpPresenter<LikesMvpView> provideFavouritesMvpPresenter(LikesPresenter<LikesMvpView> presenter) {
        return presenter;
    }

    @Provides
    LinearLayoutManager provideLinearLayoutManager(AppCompatActivity activity) {
        return new LinearLayoutManager(activity);
    }

    @Provides
    StaggeredGridLayoutManager provideGridLayoutManager() {
        return new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL) {
            @Override
            public boolean supportsPredictiveItemAnimations() {
                return true;
            }
        };
    }
}
