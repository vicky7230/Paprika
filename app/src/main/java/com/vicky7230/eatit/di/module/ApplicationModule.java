package com.vicky7230.eatit.di.module;

import android.content.Context;

import com.vicky7230.eatit.PaprikaApplication;
import com.vicky7230.eatit.data.AppDataManager;
import com.vicky7230.eatit.data.Config;
import com.vicky7230.eatit.data.DataManager;
import com.vicky7230.eatit.data.db.AppDbHelper;
import com.vicky7230.eatit.data.db.DbHelper;
import com.vicky7230.eatit.data.network.ApiHelper;
import com.vicky7230.eatit.data.network.AppApiHelper;
import com.vicky7230.eatit.di.ApplicationContext;
import com.vicky7230.eatit.di.DatabaseInfo;
import com.vicky7230.eatit.di.ImaggaBaseUrl;
import com.vicky7230.eatit.di.RecipesBaseUrl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;

@Module
public class ApplicationModule {

    @Provides
    @ApplicationContext
    Context provideContext(PaprikaApplication paprikaApplication) {
        return paprikaApplication.getApplicationContext();
    }

    @Provides
    CompositeDisposable provideCompositeDisposable() {
        return new CompositeDisposable();
    }

    @Provides
    @RecipesBaseUrl
    String provideRecipesBaseUrl() {
        return Config.RECIPES_BASE_URL;
    }

    @Provides
    @ImaggaBaseUrl
    String provideImaggaBaseUrl() {
        return Config.IMAGGA_BASE_URL;
    }

    @Provides
    @DatabaseInfo
    String provideDatabaseName() {
        return Config.DB_NAME;
    }

    @Provides
    @Singleton
    DataManager provideDataManager(AppDataManager appDataManager) {
        return appDataManager;
    }

    @Provides
    @Singleton
    DbHelper provideDbHelper(AppDbHelper appDbHelper) {
        return appDbHelper;
    }

    @Provides
    @Singleton
    ApiHelper provideApiHelper(AppApiHelper appApiHelper) {
        return appApiHelper;
    }

}
