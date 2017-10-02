package com.vicky7230.eatit.ui.splash;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;

import com.vicky7230.eatit.R;
import com.vicky7230.eatit.ui.base.BaseActivity;
import com.vicky7230.eatit.ui.home.HomeActivity;

import javax.inject.Inject;

import butterknife.ButterKnife;
import dagger.android.AndroidInjection;

public class SplashActivity extends BaseActivity implements SplashMvpView {

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    @Inject
    SplashMvpPresenter<SplashMvpView> presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        presenter.onAttach(this);
        init();
    }

    private void init() {
        presenter.startCounting();
    }

    @Override
    public void launchHomeActivity() {
        startActivity(new Intent(this, HomeActivity.class));
        finish();
    }

    @Override
    protected void onDestroy() {
        presenter.onDetach();
        super.onDestroy();
    }
}
