package com.vicky7230.eatit.di.component;

import com.vicky7230.eatit.di.module.ActivityModule;
import com.vicky7230.eatit.di.module.ApplicationModule;
import com.vicky7230.eatit.di.module.NetworkModule;
import com.vicky7230.eatit.ui.home.HomeActivity;
import com.vicky7230.eatit.ui.home.likes.LikesFragment;
import com.vicky7230.eatit.ui.home.Imagga.ImaggaFragment;
import com.vicky7230.eatit.ui.home.recipes.RecipesFragment;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by vicky on 25/6/17.
 */

@Singleton
@Component(modules = {
        NetworkModule.class,
        ActivityModule.class,
        ApplicationModule.class
})
public interface ActivityComponent {

    void inject(HomeActivity homeActivity);

    void inject(RecipesFragment recipesFragment);

    void inject(LikesFragment likesFragment);

    void inject(ImaggaFragment imaggaFragment);

}
