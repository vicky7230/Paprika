package com.vicky7230.eatit.ui.base;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.vicky7230.eatit.R;
import com.vicky7230.eatit.data.Config;
import com.vicky7230.eatit.di.component.ActivityComponent;
import com.vicky7230.eatit.di.component.DaggerActivityComponent;
import com.vicky7230.eatit.di.module.ActivityModule;
import com.vicky7230.eatit.di.module.ApplicationModule;
import com.vicky7230.eatit.di.module.NetworkModule;
import com.vicky7230.eatit.utils.CommonUtils;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class BaseActivity extends AppCompatActivity implements MvpView {

    private Dialog progressDialog;

    private ActivityComponent activityComponent;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityComponent = DaggerActivityComponent.builder()
                .activityModule(new ActivityModule(this))
                .networkModule(new NetworkModule(Config.RECIPES_BASE_URL, Config.IMAGGA_BASE_URL))
                .applicationModule(new ApplicationModule(getApplication()))
                .build();

    }

    public ActivityComponent getActivityComponent() {
        return activityComponent;
    }

    private void displayMessage(String message) {
        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT);
        View snackBarView = snackbar.getView();
        snackBarView.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary));
        TextView textView = snackBarView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(ContextCompat.getColor(this, android.R.color.white));
        snackbar.show();
    }

    private void displayError(String message) {
        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT);
        View snackBarView = snackbar.getView();
        snackBarView.setBackgroundColor(ContextCompat.getColor(this, R.color.colorRedError));
        TextView textView = snackBarView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(ContextCompat.getColor(this, android.R.color.white));
        snackbar.show();
    }

    @Override
    public void showLoading() {
        hideLoading();
        progressDialog = CommonUtils.showLoadingDialog(this);
    }

    @Override
    public void hideLoading() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.cancel();
        }
    }

    @Override
    public void showMessage(String message) {
        if (message != null)
            displayMessage(message);
    }

    @Override
    public void showError(String message) {
        if (message != null)
            displayError(message);
    }
}
