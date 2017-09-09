package com.vicky7230.eatit.ui.home.recipes;

import android.support.v7.util.DiffUtil;

import com.vicky7230.eatit.data.network.model.recipes.Recipe;

import java.util.List;

/**
 * Created by agrim on 4/9/17.
 */

public class RecipeDiffUtilCallback extends DiffUtil.Callback {

    private List<Recipe> oldRecipeList;
    private List<Recipe> newRecipeList;

    public RecipeDiffUtilCallback(List<Recipe> oldRecipeList, List<Recipe> newRecipeList) {
        this.oldRecipeList = oldRecipeList;
        this.newRecipeList = newRecipeList;
    }

    @Override
    public int getOldListSize() {
        return oldRecipeList.size();
    }

    @Override
    public int getNewListSize() {
        return newRecipeList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return !(oldRecipeList.get(oldItemPosition) == null || newRecipeList.get(newItemPosition) == null) && oldRecipeList.get(oldItemPosition).getRecipeId().equals(newRecipeList.get(newItemPosition).getRecipeId());
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return !(oldRecipeList.get(oldItemPosition) == null || newRecipeList.get(newItemPosition) == null) && oldRecipeList.get(oldItemPosition).equals(newRecipeList.get(newItemPosition));
    }
}
