package com.vicky7230.eatit.ui.search;

import com.vicky7230.eatit.data.network.model.recipes.Recipe;
import com.vicky7230.eatit.ui.base.MvpView;

import java.util.List;

/**
 * Created by agrim on 15/9/17.
 */

public interface SearchMvpView extends MvpView {

    void reFreshRecipeList(List<Recipe> recipeList);
}
