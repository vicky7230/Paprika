package com.vicky7230.eatit.ui.home.recipes;

import com.vicky7230.eatit.data.network.model.recipes.Recipe;
import com.vicky7230.eatit.ui.base.MvpView;

import java.util.List;

/**
 * Created by agrim on 3/7/17.
 */

public interface RecipesMvpView extends MvpView {

    void updateRecipeList(List<Recipe> resultList);

    void showErrorInRecyclerView();

    void showIngredients(List<String> ingredients);
}
