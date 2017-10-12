package com.vicky7230.eatit.ui.search;

import android.support.v7.widget.AppCompatEditText;

import com.jakewharton.rxbinding2.InitialValueObservable;
import com.jakewharton.rxbinding2.widget.TextViewAfterTextChangeEvent;
import com.vicky7230.eatit.ui.base.MvpPresenter;

import java.util.Observable;

public interface SearchMvpPresenter<V extends SearchMvpView> extends MvpPresenter<V> {

    void instantSearch(InitialValueObservable<TextViewAfterTextChangeEvent> observable);
}
