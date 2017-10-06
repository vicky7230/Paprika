package com.vicky7230.eatit.ui.home;

import com.vicky7230.eatit.data.DataManager;
import com.vicky7230.eatit.rxBus.RxBus;
import com.vicky7230.eatit.rxBus.events.LikesUpdatedEvent;
import com.vicky7230.eatit.rxBus.events.RecipeSingleClickEvent;
import com.vicky7230.eatit.ui.base.BasePresenter;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import timber.log.Timber;

public class HomePresenter<V extends HomeMvpView> extends BasePresenter<V> implements HomeMvpPresenter<V> {

    @Inject
    public HomePresenter(DataManager dataManager, CompositeDisposable compositeDisposable) {
        super(dataManager, compositeDisposable);
        RxBus.subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {

                if (o instanceof RecipeSingleClickEvent) {
                    getMvpView().showBottomSheet(((RecipeSingleClickEvent) o).getRecipe());
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                getMvpView().showError(throwable.getMessage());
            }
        });
    }
}
