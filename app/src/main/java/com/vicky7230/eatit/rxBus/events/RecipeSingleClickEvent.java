package com.vicky7230.eatit.rxBus.events;

import com.vicky7230.eatit.data.network.model.recipes.Recipe;

/**
 * Created by vicky on 6/10/17.
 */

public class RecipeSingleClickEvent {

    private Recipe recipe;

    public RecipeSingleClickEvent(Recipe recipe) {
        this.recipe = recipe;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }
}
