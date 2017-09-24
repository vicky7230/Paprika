package com.vicky7230.eatit.data.db;

import com.vicky7230.eatit.data.db.model.DaoMaster;
import com.vicky7230.eatit.data.db.model.DaoSession;
import com.vicky7230.eatit.data.db.model.LikedRecipe;
import com.vicky7230.eatit.data.db.model.LikedRecipeDao;
import com.vicky7230.eatit.data.network.model.recipes.Recipe;

import java.util.List;
import java.util.concurrent.Callable;

import javax.inject.Inject;

import io.reactivex.Observable;

public class AppDbHelper implements DbHelper {

    private final DaoSession daoSession;

    @Inject
    public AppDbHelper(DbOpenHelper dbOpenHelper) {
        daoSession = new DaoMaster(dbOpenHelper.getWritableDb()).newSession();
    }

    @Override
    public Boolean checkIfRecipeIsLiked(final Recipe recipe) {
        List<LikedRecipe> list = daoSession.getLikedRecipeDao().queryBuilder().where(LikedRecipeDao.Properties.RecipeId.eq(recipe.getRecipeId())).list();
        return list != null && list.size() > 0;
    }

    @Override
    public Observable<List<LikedRecipe>> checkIfRecipeIsAlreadyLiked(final Recipe recipe) {
        return Observable.fromCallable(new Callable<List<LikedRecipe>>() {
            @Override
            public List<LikedRecipe> call() throws Exception {
                return daoSession.getLikedRecipeDao().queryBuilder().where(LikedRecipeDao.Properties.RecipeId.eq(recipe.getRecipeId())).list();
            }
        });
    }

    @Override
    public Observable<Long> insertLikedRecipe(final LikedRecipe likedRecipe) {
        return Observable.fromCallable(new Callable<Long>() {
            @Override
            public Long call() throws Exception {
                return daoSession.getLikedRecipeDao().insert(likedRecipe);
            }
        });
    }

    @Override
    public Observable<List<LikedRecipe>> getAllLikedRecipes() {
        return Observable.fromCallable(new Callable<List<LikedRecipe>>() {
            @Override
            public List<LikedRecipe> call() throws Exception {
                return daoSession.getLikedRecipeDao().loadAll();
            }
        });
    }

    @Override
    public Observable<List<LikedRecipe>> getLastInsertedLikedRecipe() {
        return Observable.fromCallable(new Callable<List<LikedRecipe>>() {
            @Override
            public List<LikedRecipe> call() throws Exception {
                return daoSession.getLikedRecipeDao().queryBuilder().orderDesc(LikedRecipeDao.Properties.Id).limit(1).list();
            }
        });
    }

    @Override
    public Observable<Boolean> deleteLikedRecipe(final LikedRecipe likedRecipe) {
        return Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                daoSession.getLikedRecipeDao().deleteByKey(likedRecipe.getId());
                return true;
            }
        });
    }
}
