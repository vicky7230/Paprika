package com.vicky7230.eatit.ui.home;

import android.support.v4.app.Fragment;

import com.vicky7230.eatit.ui.home.Imagga.ImaggaFragment;
import com.vicky7230.eatit.ui.home.Imagga.ImaggaModule;
import com.vicky7230.eatit.ui.home.likes.LikesFragment;
import com.vicky7230.eatit.ui.home.likes.LikesModule;
import com.vicky7230.eatit.ui.home.recipes.RecipesFragment;
import com.vicky7230.eatit.ui.home.recipes.RecipesModule;

import dagger.Binds;
import dagger.Module;
import dagger.android.AndroidInjector;
import dagger.android.ContributesAndroidInjector;
import dagger.android.FragmentKey;
import dagger.multibindings.IntoMap;

/**
 * Created by vipin on 17/9/17.
 */


@Module
public abstract class FragmentProvider {

    @ContributesAndroidInjector(modules = RecipesModule.class)
    abstract RecipesFragment provideRecipesFragmentFactory();

    @ContributesAndroidInjector(modules = LikesModule.class)
    abstract LikesFragment provideLikesFragmentFactory();

    @ContributesAndroidInjector(modules = ImaggaModule.class)
    abstract ImaggaFragment provideImaggaFragmentFactory();
}