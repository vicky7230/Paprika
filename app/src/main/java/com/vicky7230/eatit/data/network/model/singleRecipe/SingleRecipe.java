
package com.vicky7230.eatit.data.network.model.singleRecipe;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SingleRecipe {

    @SerializedName("recipe")
    @Expose
    private Recipe recipe;

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

}
