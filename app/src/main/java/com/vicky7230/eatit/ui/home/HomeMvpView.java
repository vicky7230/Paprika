package com.vicky7230.eatit.ui.home;

import com.vicky7230.eatit.data.network.model.recipes.Recipe;
import com.vicky7230.eatit.ui.base.MvpView;

public interface HomeMvpView extends MvpView{

    void showBottomSheet(Recipe recipe);
}
