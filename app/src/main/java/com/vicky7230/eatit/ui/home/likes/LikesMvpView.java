package com.vicky7230.eatit.ui.home.likes;

import com.vicky7230.eatit.data.db.entity.LikedRecipe;
import com.vicky7230.eatit.ui.base.MvpView;

import java.util.List;

/**
 * Created by agrim on 29/7/17.
 */

public interface LikesMvpView extends MvpView{

    void updateFavouriteRecipeList(List<LikedRecipe> likedRecipeList);

    void removeRecipeFromFavouriteListView(int position);
}
