package com.vicky7230.eatit.ui.search;

import android.support.v7.widget.AppCompatEditText;

import com.vicky7230.eatit.ui.base.MvpPresenter;

public interface SearchMvpPresenter<V extends SearchMvpView> extends MvpPresenter<V> {

    void instantSearch(AppCompatEditText appCompatEditText);
}
