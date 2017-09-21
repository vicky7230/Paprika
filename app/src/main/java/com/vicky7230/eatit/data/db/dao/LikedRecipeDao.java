package com.vicky7230.eatit.data.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.vicky7230.eatit.data.db.entity.LikedRecipe;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface LikedRecipeDao {

    @Query("SELECT * FROM liked_recipes")
    Flowable<List<LikedRecipe>> getAllLikedRecipes();

    @Query("SELECT * FROM liked_recipes WHERE recipe_id = :recipeId")
    List<LikedRecipe> checkIfRecipeIsLiked(String recipeId);

    @Query("SELECT * FROM liked_recipes WHERE recipe_id = :recipeId")
    Flowable<List<LikedRecipe>> checkIfRecipeIsAlreadyLiked(String recipeId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insertLikedRecipe(LikedRecipe likedRecipe);

    @Query("SELECT * FROM liked_recipes ORDER BY id DESC LIMIT 0,1")
    Flowable<List<LikedRecipe>> getLastInsertedLikedRecipe();

}
