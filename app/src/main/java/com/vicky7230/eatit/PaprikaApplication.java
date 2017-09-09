package com.vicky7230.eatit;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.IoniconsModule;
import com.joanzapata.iconify.fonts.MaterialModule;

import timber.log.Timber;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by vicky on 25/6/17.
 */

public class PaprikaApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        //Stetho initialization
        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this);
        }

        //Timber initialization
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }

        //Calligraphy initialization
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/OpenSans-Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );

        //Iconify Initialization
        Iconify
                .with(new IoniconsModule())
                .with(new MaterialModule());
    }

}
