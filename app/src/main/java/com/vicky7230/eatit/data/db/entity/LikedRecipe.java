package com.vicky7230.eatit.data.db.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by agrim on 21/7/17.
 */

@Entity(tableName = "liked_recipes")
public class LikedRecipe {

    @PrimaryKey(autoGenerate = true)
    private Long id;

    @ColumnInfo(name = "recipe_id")
    private String recipeId;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "image_url")
    private String imageUrl;

    @ColumnInfo(name = "source_url")
    private String sourceUrl;

    public LikedRecipe(String recipeId, String title, String imageUrl, String sourceUrl) {
        this.recipeId = recipeId;
        this.title = title;
        this.imageUrl = imageUrl;
        this.sourceUrl = sourceUrl;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(String recipeId) {
        this.recipeId = recipeId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }
}
