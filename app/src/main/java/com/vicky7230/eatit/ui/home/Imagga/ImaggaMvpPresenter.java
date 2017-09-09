package com.vicky7230.eatit.ui.home.Imagga;

import android.content.Intent;

import com.vicky7230.eatit.ui.base.MvpPresenter;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by agrim on 11/8/17.
 */

public interface ImaggaMvpPresenter<V extends ImaggaMvpView> extends MvpPresenter<V> {

    void uploadImageToImagga(String imagePath);
}
