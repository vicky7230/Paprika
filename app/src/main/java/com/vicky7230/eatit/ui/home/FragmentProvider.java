package com.vicky7230.eatit.ui.home;

import com.vicky7230.eatit.ui.home.Imagga.ImaggaFragment;
import com.vicky7230.eatit.ui.home.Imagga.ImaggaModule;
import com.vicky7230.eatit.ui.home.likes.LikesFragment;
import com.vicky7230.eatit.ui.home.likes.LikesModule;
import com.vicky7230.eatit.ui.home.recipes.RecipesFragment;
import com.vicky7230.eatit.ui.home.recipes.RecipesModule;
import com.vicky7230.eatit.ui.home.settings.SettingsFragment;
import com.vicky7230.eatit.ui.home.settings.SettingsModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FragmentProvider {

    @ContributesAndroidInjector(modules = RecipesModule.class)
    abstract RecipesFragment provideRecipesFragmentFactory();

    @ContributesAndroidInjector(modules = LikesModule.class)
    abstract LikesFragment provideLikesFragmentFactory();

    @ContributesAndroidInjector(modules = ImaggaModule.class)
    abstract ImaggaFragment provideImaggaFragmentFactory();

    @ContributesAndroidInjector(modules = SettingsModule.class)
    abstract SettingsFragment provideSettingsFragmentFactory();
}