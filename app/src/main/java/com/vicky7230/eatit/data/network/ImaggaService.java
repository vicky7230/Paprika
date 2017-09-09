package com.vicky7230.eatit.data.network;

import com.vicky7230.eatit.data.network.model.imagga.content.Content;
import com.vicky7230.eatit.data.network.model.imagga.tag.Tags;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * Created by vicky on 25/6/17.
 */

public interface ImaggaService {

    @Headers("Authorization: Basic YWNjXzAyMmE3M2I4OWFkZjc1YzpjNmNhZWVlNjA4N2MxMzMzOWEwYTc0OGZhZTAyZTEwMg==")
    @Multipart
    @POST("content")
    Observable<Content> uploadImage(
            @Part("image") RequestBody image,
            @Part MultipartBody.Part file
    );

    @Headers({
            "Accept: application/json",
            "Authorization: Basic YWNjXzAyMmE3M2I4OWFkZjc1YzpjNmNhZWVlNjA4N2MxMzMzOWEwYTc0OGZhZTAyZTEwMg=="
    })
    @GET("tagging")
    Observable<Tags> tagImage(
            @Query("content") String content,
            @Query("limit") int limit
    );
}
