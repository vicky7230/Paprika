package com.vicky7230.eatit.data;

import com.vicky7230.eatit.data.db.DbHelper;
import com.vicky7230.eatit.data.db.entity.LikedRecipe;
import com.vicky7230.eatit.data.network.ApiHelper;
import com.vicky7230.eatit.data.network.model.imagga.content.Content;
import com.vicky7230.eatit.data.network.model.imagga.tag.Tags;
import com.vicky7230.eatit.data.network.model.recipes.Recipe;
import com.vicky7230.eatit.data.network.model.recipes.Recipes;
import com.vicky7230.eatit.data.network.model.singleRecipe.SingleRecipe;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

@Singleton
public class AppDataManager implements DataManager {

    private final ApiHelper apiHelper;
    private final DbHelper dbHelper;

    @Inject
    public AppDataManager(ApiHelper apiHelper, DbHelper dbHelper) {
        this.apiHelper = apiHelper;
        this.dbHelper = dbHelper;
    }

    @Override
    public Observable<Recipes> getRecipes(String page) {
        return apiHelper.getRecipes(page);
    }

    @Override
    public Observable<SingleRecipe> getRecipe(String rId) {
        return apiHelper.getRecipe(rId);
    }

    @Override
    public Observable<Content> uploadImage(RequestBody name, MultipartBody.Part body) {
        return apiHelper.uploadImage(name, body);
    }

    @Override
    public Observable<Tags> tagImage(String content, int limit) {
        return apiHelper.tagImage(content, limit);
    }

    @Override
    public List<LikedRecipe> checkIfRecipeIsLiked(Recipe recipe) {
        return dbHelper.checkIfRecipeIsLiked(recipe);
    }

    @Override
    public Flowable<List<LikedRecipe>> checkIfRecipeIsAlreadyLiked(Recipe recipe) {
        return dbHelper.checkIfRecipeIsAlreadyLiked(recipe);
    }

    @Override
    public Flowable<Long> insertLikedRecipe(LikedRecipe likedRecipe) {
        return dbHelper.insertLikedRecipe(likedRecipe);
    }

    @Override
    public Flowable<List<LikedRecipe>> getAllLikedRecipes() {
        return dbHelper.getAllLikedRecipes();
    }

    @Override
    public Flowable<List<LikedRecipe>> getLastInsertedLikedRecipe() {
        return dbHelper.getLastInsertedLikedRecipe();
    }

}
