package com.vicky7230.eatit.data;

import com.vicky7230.eatit.data.db.DbHelper;
import com.vicky7230.eatit.data.db.model.LikedRecipe;
import com.vicky7230.eatit.data.network.ApiHelper;
import com.vicky7230.eatit.data.network.model.imagga.content.Content;
import com.vicky7230.eatit.data.network.model.imagga.tag.Tags;
import com.vicky7230.eatit.data.network.model.recipes.Recipe;
import com.vicky7230.eatit.data.network.model.recipes.Recipes;
import com.vicky7230.eatit.data.network.model.singleRecipe.SingleRecipe;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

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
    public Observable<Recipes> searchRecipes(String query, String page) {
        return apiHelper.searchRecipes(query, page);
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
    public Boolean checkIfRecipeIsLiked(Recipe recipe) {
        return dbHelper.checkIfRecipeIsLiked(recipe);
    }

    @Override
    public Observable<List<LikedRecipe>> checkIfRecipeIsAlreadyLiked(Recipe recipe) {
        return dbHelper.checkIfRecipeIsAlreadyLiked(recipe);
    }

    @Override
    public Observable<Long> insertLikedRecipe(LikedRecipe likedRecipe) {
        return dbHelper.insertLikedRecipe(likedRecipe);
    }

    @Override
    public Observable<List<LikedRecipe>> getAllLikedRecipes() {
        return dbHelper.getAllLikedRecipes();
    }

    @Override
    public Observable<List<LikedRecipe>> getLastInsertedLikedRecipe() {
        return dbHelper.getLastInsertedLikedRecipe();
    }

    @Override
    public Observable<Boolean> deleteLikedRecipe(LikedRecipe likedRecipe) {
        return dbHelper.deleteLikedRecipe(likedRecipe);
    }
}
