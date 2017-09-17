package com.vicky7230.eatit.di.component;

import android.app.Application;

import com.vicky7230.eatit.PaprikaApplication;
import com.vicky7230.eatit.di.module.ActivityBuilder;
import com.vicky7230.eatit.di.module.ApplicationModule;
import com.vicky7230.eatit.di.module.NetworkModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.support.AndroidSupportInjectionModule;

/**
 * Created by vicky on 25/6/17.
 */

@Singleton
@Component(modules = {
        AndroidSupportInjectionModule.class,
        NetworkModule.class,
        ApplicationModule.class,
        ActivityBuilder.class
})
public interface ApplicationComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(PaprikaApplication paprikaApplication);

        ApplicationComponent build();
    }

    void inject(PaprikaApplication paprikaApplication);

}
