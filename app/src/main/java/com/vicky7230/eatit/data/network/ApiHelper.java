package com.vicky7230.eatit.data.network;

import com.vicky7230.eatit.data.network.model.imagga.content.Content;
import com.vicky7230.eatit.data.network.model.imagga.tag.Tags;
import com.vicky7230.eatit.data.network.model.recipes.Recipes;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by vicky on 25/6/17.
 */

public interface ApiHelper {

    Observable<Recipes> getRecipes(String p);

    Observable<Content> uploadImage(RequestBody name, MultipartBody.Part body);

    Observable<Tags> tagImage(String content, int limit);
}
