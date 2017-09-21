package com.vicky7230.eatit.ui.home.likes;

import com.vicky7230.eatit.data.DataManager;
import com.vicky7230.eatit.data.db.entity.LikedRecipe;
import com.vicky7230.eatit.rxBus.RxBus;
import com.vicky7230.eatit.rxBus.events.LikesUpdatedEvent;
import com.vicky7230.eatit.ui.base.BasePresenter;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by agrim on 29/7/17.
 */

public class LikesPresenter<V extends LikesMvpView> extends BasePresenter<V> implements LikesMvpPresenter<V> {

    @Inject
    public LikesPresenter(DataManager dataManager, CompositeDisposable compositeDisposable) {
        super(dataManager, compositeDisposable);

        RxBus.subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {

                if (o instanceof LikesUpdatedEvent)
                    loadLastInsertedFavouriteRecipe();
            }
        });
    }

    @Override
    public void loadAllFavouriteRecipes() {

        getCompositeDisposable().add(getDataManager()
                .getAllLikedRecipes()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<LikedRecipe>>() {
                    @Override
                    public void accept(List<LikedRecipe> likedRecipeList) throws Exception {
                        if (likedRecipeList != null)
                            getMvpView().updateFavouriteRecipeList(likedRecipeList);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                        Timber.e(throwable);
                        getMvpView().showError(throwable.getMessage());
                    }
                })
        );
    }

    @Override
    public void loadLastInsertedFavouriteRecipe() {

        getCompositeDisposable().add(getDataManager()
                .getLastInsertedLikedRecipe()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<LikedRecipe>>() {
                    @Override
                    public void accept(List<LikedRecipe> likedRecipeList) throws Exception {
                        if (likedRecipeList != null)
                            Timber.d("count : " + likedRecipeList.size());
                        getMvpView().updateFavouriteRecipeList(likedRecipeList);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Timber.e(throwable);
                        getMvpView().showError(throwable.getMessage());
                    }
                })
        );
    }

    @Override
    public void removeRecipeFromFavourites(LikedRecipe likedRecipe, final int position) {

        /*getCompositeDisposable().add(getDataManager()
                .deleteLikedRecipe(likedRecipe)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean != null)
                            if (aBoolean) {
                                getMvpView().showMessage("Recipe removed from favourites.");
                                getMvpView().removeRecipeFromFavouriteListView(position);
                            }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Timber.e(throwable);//TODO
                        getMvpView().showError(throwable.getMessage());
                    }
                })
        );*/
    }

}
