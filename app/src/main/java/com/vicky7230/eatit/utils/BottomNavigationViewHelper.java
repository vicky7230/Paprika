package com.vicky7230.eatit.utils;

import android.annotation.SuppressLint;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.widget.AppCompatImageView;
import android.view.Gravity;
import android.widget.FrameLayout;

import java.lang.reflect.Field;

import timber.log.Timber;

/**
 * Created by vicky on 2/10/17.
 */

public class BottomNavigationViewHelper {

    @SuppressLint("RestrictedApi")
    public static void disableShiftMode(BottomNavigationView view) {
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);
        try {
            Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
            shiftingMode.setAccessible(true);
            shiftingMode.setBoolean(menuView, false);
            shiftingMode.setAccessible(false);
            for (int i = 0; i < menuView.getChildCount(); i++) {
                BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
                AppCompatImageView icon = (AppCompatImageView) item.getChildAt(0);

                FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) icon.getLayoutParams();
                params.gravity = Gravity.CENTER;
                //noinspection RestrictedApi
                item.setShiftingMode(true);
                // set once again checked value, so view will be updated
                //noinspection RestrictedApi
                //item.setChecked(item.getItemData().isChecked());
            }
        } catch (NoSuchFieldException e) {
            Timber.e("BNVHelper", "Unable to get shift mode field", e);
        } catch (IllegalAccessException e) {
            Timber.e("BNVHelper", "Unable to change value of shift mode", e);
        }
    }
}
