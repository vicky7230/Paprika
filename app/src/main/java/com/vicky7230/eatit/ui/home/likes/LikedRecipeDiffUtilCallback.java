package com.vicky7230.eatit.ui.home.likes;

import android.support.v7.util.DiffUtil;

import com.vicky7230.eatit.data.db.model.LikedRecipe;
import com.vicky7230.eatit.data.network.model.recipes.Recipe;

import java.util.List;

/**
 * Created by agrim on 4/9/17.
 */

public class LikedRecipeDiffUtilCallback extends DiffUtil.Callback {

    private List<LikedRecipe> oldRecipeList;
    private List<LikedRecipe> newRecipeList;

    public LikedRecipeDiffUtilCallback(List<LikedRecipe> oldRecipeList, List<LikedRecipe> newRecipeList) {
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
