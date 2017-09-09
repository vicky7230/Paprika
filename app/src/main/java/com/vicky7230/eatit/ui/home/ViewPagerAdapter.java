package com.vicky7230.eatit.ui.home;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.vicky7230.eatit.ui.home.likes.LikesFragment;
import com.vicky7230.eatit.ui.home.Imagga.ImaggaFragment;
import com.vicky7230.eatit.ui.home.recipes.RecipesFragment;
import com.vicky7230.eatit.ui.home.settings.SettingsFragment;

/**
 * Created by agrim on 2/7/17.
 */

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return RecipesFragment.newInstance();
            case 1:
                return ImaggaFragment.newInstance();
            case 2:
                return LikesFragment.newInstance();
            case 3:
                return SettingsFragment.newInstance();
            default:
                return null;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {

        return null;
    }

    @Override
    public int getCount() {
        return 4;
    }
}
