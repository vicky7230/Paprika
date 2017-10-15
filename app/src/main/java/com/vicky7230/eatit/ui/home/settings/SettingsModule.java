package com.vicky7230.eatit.ui.home.settings;

import android.content.Context;

import com.franmontiel.attributionpresenter.AttributionPresenter;
import com.franmontiel.attributionpresenter.entities.Attribution;
import com.franmontiel.attributionpresenter.entities.Library;
import com.franmontiel.attributionpresenter.entities.License;
import com.vicky7230.eatit.di.HomeActivityContext;

import dagger.Module;
import dagger.Provides;

/**
 * Created by vipin on 17/9/17.
 */

@Module
public class SettingsModule {

    @Provides
    AttributionPresenter provideAttributionPresenter(@HomeActivityContext Context context) {
        return new AttributionPresenter.Builder(context)
                .addAttributions(
                        Library.RETROFIT,
                        Library.OK_HTTP,
                        Library.BUTTER_KNIFE,
                        Library.GLIDE,
                        Library.DAGGER_2,
                        Library.GSON)
                .addAttributions(
                        new Attribution.Builder("Timber")
                                .addCopyrightNotice("Copyright 2013 Jake Wharton")
                                .addLicense(License.APACHE)
                                .setWebsite("https://github.com/JakeWharton/timber")
                                .build()
                )
                .addAttributions(
                        new Attribution.Builder("Calligraphy")
                                .addCopyrightNotice("Copyright 2013 Christopher Jenkins")
                                .addLicense(License.APACHE)
                                .setWebsite("https://github.com/chrisjenx/Calligraphy")
                                .build()
                )
                .addAttributions(
                        new Attribution.Builder("Iconify")
                                .addCopyrightNotice("Copyright 2015 Joan Zapata")
                                .addLicense(License.APACHE)
                                .setWebsite("https://github.com/JoanZapata/android-iconify")
                                .build()
                )
                .addAttributions(
                        new Attribution.Builder("GreenDAO")
                                .addCopyrightNotice("Copyright Greenrobot.org")
                                .addLicense(License.APACHE)
                                .setWebsite("http://greenrobot.org/greendao/")
                                .build()
                )
                .addAttributions(
                        new Attribution.Builder("AttributionPresenter")
                                .addCopyrightNotice("Copyright 2017 Francisco José Montiel Navarro")
                                .addLicense(License.APACHE)
                                .setWebsite("https://github.com/franmontiel/AttributionPresenter")
                                .build()
                )
                .addAttributions(
                        new Attribution.Builder("RxJava2")
                                .addCopyrightNotice("Copyright 2016-present, RxJava Contributors.")
                                .addLicense(License.APACHE)
                                .setWebsite("https://github.com/ReactiveX/RxJava")
                                .build()
                )
                .addAttributions(
                        new Attribution.Builder("RxAndroid")
                                .addCopyrightNotice("Copyright 2015 The RxAndroid authors")
                                .addLicense(License.APACHE)
                                .setWebsite("https://github.com/ReactiveX/RxAndroid")
                                .build()
                )
                .addAttributions(
                        new Attribution.Builder("RxBindings")
                                .addCopyrightNotice("Copyright (C) 2015 Jake Wharton")
                                .addLicense(License.APACHE)
                                .setWebsite("https://github.com/JakeWharton/RxBinding")
                                .build()
                )
                .build();
    }


}
