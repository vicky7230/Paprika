package com.vicky7230.eatit.di.module;

import android.app.Application;
import android.content.Context;

import com.vicky7230.eatit.data.AppDataManager;
import com.vicky7230.eatit.data.Config;
import com.vicky7230.eatit.data.DataManager;
import com.vicky7230.eatit.data.db.AppDbHelper;
import com.vicky7230.eatit.data.db.DbHelper;
import com.vicky7230.eatit.data.network.ApiHelper;
import com.vicky7230.eatit.data.network.AppApiHelper;
import com.vicky7230.eatit.di.ApplicationContext;
import com.vicky7230.eatit.di.DatabaseInfo;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by vicky on 25/6/17.
 */

@Module
public class ApplicationModule {

    private final Application application;

    public ApplicationModule(Application application) {
        this.application = application;
    }

    @Provides
    @ApplicationContext
    Context provideContext() {
        return application;
    }

    @Provides
    Application provideApplication() {
        return application;
    }

    @Provides
    @Singleton
    DataManager provideDataManager(AppDataManager appDataManager) {
        return appDataManager;
    }

    @Provides
    @Singleton
    ApiHelper provideApiHelper(AppApiHelper appApiHelper) {
        return appApiHelper;
    }

    @Provides
    @DatabaseInfo
    String provideDatabaseName() {
        return Config.DB_NAME;
    }

    @Provides
    @Singleton
    DbHelper provideDbHelper(AppDbHelper appDbHelper) {
        return appDbHelper;
    }

}
