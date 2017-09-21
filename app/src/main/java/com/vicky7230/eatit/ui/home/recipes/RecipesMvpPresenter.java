package com.vicky7230.eatit.ui.home.recipes;

import com.vicky7230.eatit.data.network.model.recipes.Recipe;
import com.vicky7230.eatit.ui.base.MvpPresenter;

/**
 * Created by agrim on 3/7/17.
 */

public interface RecipesMvpPresenter<V extends RecipesMvpView> extends MvpPresenter<V> {

    void onViewPrepared();

    void fetchRecipes();

    void likeTheRecipe(Recipe recipe);

    void getSingleRecipe(String recipeId);
}
