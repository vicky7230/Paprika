package com.vicky7230.eatit.ui.home.recipes;

import android.support.v7.widget.StaggeredGridLayoutManager;

import com.vicky7230.eatit.data.network.model.recipes.Recipe;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;

/**
 * Created by vipin on 17/9/17.
 */

@Module
public class RecipesModule {

    @Provides
    StaggeredGridLayoutManager provideGridLayoutManager() {
        return new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL) {
            @Override
            public boolean supportsPredictiveItemAnimations() {
                return true;
            }
        };
    }

    @Provides
    RecipesAdapter provideRecipeAdapter() {
        return new RecipesAdapter(new ArrayList<Recipe>());
    }

    @Provides
    RecipesMvpPresenter<RecipesMvpView> provideRecipesMvpPresenter(RecipesPresenter<RecipesMvpView> presenter) {
        return presenter;
    }

}
