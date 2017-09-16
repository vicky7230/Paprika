package com.vicky7230.eatit.ui.search;

import com.vicky7230.eatit.data.DataManager;
import com.vicky7230.eatit.ui.base.BasePresenter;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;


public class SearchPresenter<V extends SearchMvpView> extends BasePresenter<V> implements SearchMvpPresenter<V> {

    @Inject
    public SearchPresenter(DataManager dataManager, CompositeDisposable compositeDisposable) {
        super(dataManager, compositeDisposable);
    }
}
