package com.vicky7230.eatit.data.db;

import com.vicky7230.eatit.data.db.model.LikedRecipe;
import com.vicky7230.eatit.data.network.model.recipes.Recipe;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by agrim on 21/7/17.
 */

public interface DbHelper {

    Boolean checkIfRecipeIsLiked(final Recipe recipe);

    Observable<List<LikedRecipe>> checkIfRecipeIsAlreadyLiked(Recipe recipe);

    Observable<Long> insertLikedRecipe(final LikedRecipe likedRecipe);

    Observable<List<LikedRecipe>> getAllLikedRecipes();

    Observable<List<LikedRecipe>> getLastInsertedLikedRecipe();

    Observable<Boolean> deleteLikedRecipe(LikedRecipe likedRecipe);
}
