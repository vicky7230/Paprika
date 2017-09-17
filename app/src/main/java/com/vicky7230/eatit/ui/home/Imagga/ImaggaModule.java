package com.vicky7230.eatit.ui.home.Imagga;

import dagger.Module;
import dagger.Provides;

/**
 * Created by vipin on 17/9/17.
 */

@Module
public class ImaggaModule {

    @Provides
    ImaggaMvpPresenter<ImaggaMvpView> provideRecipeOfDayMvpPresenter(ImaggaPresenter<ImaggaMvpView> presenter) {
        return presenter;
    }

}
