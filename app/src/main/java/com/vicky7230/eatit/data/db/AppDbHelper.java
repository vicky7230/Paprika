package com.vicky7230.eatit.data.db;

import android.arch.persistence.room.Room;
import android.content.Context;

import com.vicky7230.eatit.data.db.entity.LikedRecipe;
import com.vicky7230.eatit.data.network.model.recipes.Recipe;
import com.vicky7230.eatit.di.ApplicationContext;
import com.vicky7230.eatit.di.DatabaseInfo;

import java.util.List;
import java.util.concurrent.Callable;

import javax.inject.Inject;

import io.reactivex.Flowable;

public class AppDbHelper implements DbHelper {

    private final RecipeDatabase recipeDatabase;

    @Inject
    public AppDbHelper(@ApplicationContext Context context, @DatabaseInfo String dbName) {
        recipeDatabase = Room.databaseBuilder(context, RecipeDatabase.class, dbName).build();
    }

    @Override
    public Flowable<List<LikedRecipe>> getAllLikedRecipes() {
        return recipeDatabase.likedRecipeDao().getAllLikedRecipes();
    }

    @Override
    public List<LikedRecipe> checkIfRecipeIsLiked(Recipe recipe) {
        return recipeDatabase.likedRecipeDao().checkIfRecipeIsLiked(recipe.getRecipeId());
    }

    @Override
    public Flowable<List<LikedRecipe>> checkIfRecipeIsAlreadyLiked(Recipe recipe) {
        return recipeDatabase.likedRecipeDao().checkIfRecipeIsAlreadyLiked(recipe.getRecipeId());
    }

    @Override
    public Flowable<Long> insertLikedRecipe(final LikedRecipe likedRecipe) {
        return Flowable.fromCallable(new Callable<Long>() {
            @Override
            public Long call() throws Exception {
                return recipeDatabase.likedRecipeDao().insertLikedRecipe(likedRecipe);
            }
        });
    }

    @Override
    public Flowable<List<LikedRecipe>> getLastInsertedLikedRecipe() {
        return recipeDatabase.likedRecipeDao().getLastInsertedLikedRecipe();
    }
}
