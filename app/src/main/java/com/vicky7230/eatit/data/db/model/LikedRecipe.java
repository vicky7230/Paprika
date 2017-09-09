package com.vicky7230.eatit.data.db.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by agrim on 21/7/17.
 */

@Entity(nameInDb = "liked_recipes")
public class LikedRecipe {

    @Id(autoincrement = true)
    @Property(nameInDb = "id")
    private Long id;

    @Property(nameInDb = "recipe_id")
    private String recipeId;

    @Property(nameInDb = "title")
    private String title;

    @Property(nameInDb = "image_url")
    private String imageUrl;

    @Property(nameInDb = "source_url")
    private String sourceUrl;

    @Generated(hash = 1921031656)
    public LikedRecipe(Long id, String recipeId, String title, String imageUrl,
            String sourceUrl) {
        this.id = id;
        this.recipeId = recipeId;
        this.title = title;
        this.imageUrl = imageUrl;
        this.sourceUrl = sourceUrl;
    }

    public LikedRecipe(String recipeId, String title, String imageUrl,
                       String sourceUrl) {
        this.recipeId = recipeId;
        this.title = title;
        this.imageUrl = imageUrl;
        this.sourceUrl = sourceUrl;
    }

    @Generated(hash = 6385484)
    public LikedRecipe() {
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
