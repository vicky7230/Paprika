package com.vicky7230.eatit.ui.home.likes;

import android.support.v7.widget.StaggeredGridLayoutManager;

import com.vicky7230.eatit.data.db.entity.LikedRecipe;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;


@Module
public class LikesModule {

    @Provides
    StaggeredGridLayoutManager provideGridLayoutManager() {
        return new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL) {
            @Override
            public boolean supportsPredictiveItemAnimations() {
                return true;
            }
        };
    }

    @Provides
    LikesAdapter provideLikesAdapter() {
        return new LikesAdapter(new ArrayList<LikedRecipe>());
    }

    @Provides
    LikesMvpPresenter<LikesMvpView> provideLikesMvpPresenter(LikesPresenter<LikesMvpView> presenter) {
        return presenter;
    }
}
