package com.vicky7230.eatit.ui.home.recipes;

import com.vicky7230.eatit.data.DataManager;
import com.vicky7230.eatit.data.db.model.LikedRecipe;
import com.vicky7230.eatit.data.network.model.recipes.Recipe;
import com.vicky7230.eatit.data.network.model.recipes.Recipes;
import com.vicky7230.eatit.rxBus.RxBus;
import com.vicky7230.eatit.rxBus.events.LikesUpdatedEvent;
import com.vicky7230.eatit.ui.base.BasePresenter;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class RecipesPresenter<V extends RecipesMvpView> extends BasePresenter<V> implements RecipesMvpPresenter<V> {

    private int page = 1;

    @Inject
    public RecipesPresenter(DataManager dataManager, CompositeDisposable compositeDisposable) {
        super(dataManager, compositeDisposable);
    }

    @Override
    public void onViewPrepared() {
        fetchRecipes();
    }

    @Override
    public void fetchRecipes() {
        getCompositeDisposable().add(getDataManager()
                .getRecipes(String.valueOf(page))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<Recipes, Recipes>() {
                    @Override
                    public Recipes apply(@NonNull Recipes recipes) throws Exception {
                        for (Recipe recipe : recipes.getRecipes()) {
                            if (getDataManager().checkIfRecipeIsLiked(recipe)) {
                                recipe.setLiked(true);
                            } else {
                                recipe.setLiked(false);
                            }
                        }
                        return recipes;
                    }
                })
                .subscribe(new Consumer<Recipes>() {
                    @Override
                    public void accept(Recipes recipes) throws Exception {
                        if (recipes != null && recipes.getRecipes() != null) {
                            getMvpView().updateRecipeList(recipes.getRecipes());
                            ++page;
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        if (!isViewAttached()) {
                            return;
                        }
                        handleApiError(throwable);
                        getMvpView().showErrorInRecyclerView();
                    }
                }));

    }

    @Override
    public void likeTheRecipe(final Recipe recipe) {

        getCompositeDisposable().add(getDataManager()
                .checkIfRecipeIsAlreadyLiked(recipe)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<LikedRecipe>>() {
                    @Override
                    public void accept(List<LikedRecipe> likedRecipes) throws Exception {
                        if (likedRecipes != null && likedRecipes.size() > 0) {
                            getMvpView().showMessage("Recipe Liked");
                        } else {
                            insertLikedRecipe(recipe);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        getMvpView().showError(throwable.getMessage());
                        Timber.e(throwable);
                    }
                }));
    }

    private void insertLikedRecipe(Recipe recipe) {
        Observable<Long> longObservable = getDataManager().insertLikedRecipe(
                new LikedRecipe(
                        recipe.getRecipeId(),
                        recipe.getTitle(),
                        recipe.getImageUrl(),
                        recipe.getSourceUrl()
                )
        );
        getCompositeDisposable().add(longObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        if (aLong != null) {
                            getMvpView().showMessage("Recipe Liked");
                            RxBus.publish(new LikesUpdatedEvent());
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        getMvpView().showError(throwable.getMessage());
                        Timber.e(throwable);
                    }
                }));
    }
}
