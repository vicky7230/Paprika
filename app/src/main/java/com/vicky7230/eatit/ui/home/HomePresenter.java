package com.vicky7230.eatit.ui.home;

import com.vicky7230.eatit.data.DataManager;
import com.vicky7230.eatit.ui.base.BasePresenter;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by agrim on 26/6/17.
 */

public class HomePresenter<V extends HomeMvpView> extends BasePresenter<V> implements HomeMvpPresenter<V> {

    @Inject
    public HomePresenter(DataManager dataManager, CompositeDisposable compositeDisposable) {
        super(dataManager, compositeDisposable);
    }
}
