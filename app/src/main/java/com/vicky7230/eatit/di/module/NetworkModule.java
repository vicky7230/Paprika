package com.vicky7230.eatit.di.module;

import android.content.Context;
import android.support.annotation.NonNull;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.vicky7230.eatit.data.network.ImaggaService;
import com.vicky7230.eatit.data.network.RecipeService;
import com.vicky7230.eatit.di.ApplicationContext;
import com.vicky7230.eatit.di.ApplicationInterceptor;
import com.vicky7230.eatit.di.ImaggaRetrofit;
import com.vicky7230.eatit.di.NetworkInterceptor;
import com.vicky7230.eatit.di.RecipesRetrofit;
import com.vicky7230.eatit.utils.NetworkUtils;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


@Module
public class NetworkModule {

    private final String recipesBaseUrl;
    private final String imaggaBaseUrl;

    public NetworkModule(String recipesBaseUrl, String imaggaBaseUrl) {
        this.recipesBaseUrl = recipesBaseUrl;
        this.imaggaBaseUrl = imaggaBaseUrl;
    }

    @Provides
    @Singleton
    StethoInterceptor provideStethoInterceptor() {
        return new StethoInterceptor();
    }

    @Provides
    @Singleton
    HttpLoggingInterceptor provideHttpLoggingInterceptor() {
        return new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC);
    }

    @Provides
    @Singleton
    @NetworkInterceptor
    Interceptor provideNetworkInterceptor() {
        return new Interceptor() {
            @Override
            public Response intercept(@NonNull Chain chain) throws IOException {
                Response originalResponse = chain.proceed(chain.request());
                String cacheControl = originalResponse.header("Cache-Control");
                if (cacheControl == null || cacheControl.contains("no-store") || cacheControl.contains("no-cache") ||
                        cacheControl.contains("must-revalidate") || cacheControl.contains("max-age=0")) {
                    return originalResponse.newBuilder()
                            .removeHeader("Pragma")
                            .header("Cache-Control", "public, max-age=" + 86400)// cache and reuse for 1 day(86400 seconds)
                            .build();
                } else {
                    return originalResponse;
                }
            }
        };
    }

    @Provides
    @Singleton
    @ApplicationInterceptor
    Interceptor provideInterceptor(@ApplicationContext final Context context) {
        return new Interceptor() {
            @Override
            public Response intercept(@NonNull Chain chain) throws IOException {
                Request request = chain.request();
                if (!NetworkUtils.isNetworkConnected(context)) {
                    request = request.newBuilder()
                            .cacheControl(new CacheControl.Builder().onlyIfCached().maxStale(60 * 60 * 24 * 28, TimeUnit.SECONDS).build())//max stale for 4 weeks
                            .build();
                } else {
                    request = request.newBuilder()
                            .cacheControl(new CacheControl.Builder().maxAge(0, TimeUnit.SECONDS).build())
                            .build();
                }
                return chain.proceed(request);
            }
        };
    }

    @Provides
    @Singleton
    Cache provideCache(@ApplicationContext Context context) {
        return new Cache(context.getCacheDir(), 100 * 1024 * 1024);//100 MB cache
    }

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient(Cache cache, HttpLoggingInterceptor httpLoggingInterceptor, StethoInterceptor stethoInterceptor) {
        return new OkHttpClient.Builder()
                //.cache(cache)
                .addInterceptor(httpLoggingInterceptor)
                .addNetworkInterceptor(stethoInterceptor)
                //.addNetworkInterceptor(networkInterceptor)
                //.addInterceptor(interceptor)
                .build();
    }

    @Provides
    @Singleton
    GsonConverterFactory provideGsonConverterFactory() {
        return GsonConverterFactory.create();
    }

    @Provides
    @Singleton
    RxJava2CallAdapterFactory provideRxJava2CallAdapterFactory() {
        return RxJava2CallAdapterFactory.create();
    }

    @Provides
    @Singleton
    @RecipesRetrofit
    Retrofit provideRecipesRetrofit(GsonConverterFactory gsonConverterFactory, RxJava2CallAdapterFactory rxJava2CallAdapterFactory, OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl(recipesBaseUrl)
                .addConverterFactory(gsonConverterFactory)
                .addCallAdapterFactory(rxJava2CallAdapterFactory)
                .client(okHttpClient)
                .build();
    }

    @Provides
    @Singleton
    @ImaggaRetrofit
    Retrofit provideImaggaRetrofit(GsonConverterFactory gsonConverterFactory, RxJava2CallAdapterFactory rxJava2CallAdapterFactory, OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl(imaggaBaseUrl)
                .addConverterFactory(gsonConverterFactory)
                .addCallAdapterFactory(rxJava2CallAdapterFactory)
                .client(okHttpClient)
                .build();
    }

    @Provides
    @Singleton
    RecipeService provideRecipeService(@RecipesRetrofit Retrofit retrofit) {
        return retrofit.create(RecipeService.class);
    }

    @Provides
    @Singleton
    ImaggaService provideImaggaService(@ImaggaRetrofit Retrofit retrofit) {
        return retrofit.create(ImaggaService.class);
    }
}
