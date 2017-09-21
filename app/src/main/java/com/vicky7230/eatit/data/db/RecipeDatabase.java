package com.vicky7230.eatit.data.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.vicky7230.eatit.data.db.dao.LikedRecipeDao;
import com.vicky7230.eatit.data.db.entity.LikedRecipe;

@Database(entities = {LikedRecipe.class}, version = 1, exportSchema = false)
public abstract class RecipeDatabase extends RoomDatabase {

    abstract LikedRecipeDao likedRecipeDao();
}
