package com.vicky7230.eatit.data.db;

import com.vicky7230.eatit.data.db.entity.LikedRecipe;
import com.vicky7230.eatit.data.network.model.recipes.Recipe;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;

/**
 * Created by agrim on 21/7/17.
 */

public interface DbHelper {

    Flowable<List<LikedRecipe>> getAllLikedRecipes();

    List<LikedRecipe> checkIfRecipeIsLiked(Recipe recipe);

    Flowable<List<LikedRecipe>> checkIfRecipeIsAlreadyLiked(Recipe recipe);

    Flowable<Long> insertLikedRecipe(final LikedRecipe likedRecipe);

    Flowable<List<LikedRecipe>> getLastInsertedLikedRecipe();

    //Observable<Boolean> deleteLikedRecipe(LikedRecipe likedRecipe);
}
