package com.vicky7230.eatit.ui.home.likes;

import com.vicky7230.eatit.data.db.model.LikedRecipe;
import com.vicky7230.eatit.ui.base.MvpPresenter;


public interface LikesMvpPresenter<V extends LikesMvpView> extends MvpPresenter<V> {

    void loadAllFavouriteRecipes();

    void loadLastInsertedFavouriteRecipe();

    void removeRecipeFromFavourites(LikedRecipe likedRecipe, int position);
}
