package com.vicky7230.eatit.ui.search;

import android.support.v7.widget.AppCompatEditText;

import com.jakewharton.rxbinding2.InitialValueObservable;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.jakewharton.rxbinding2.widget.TextViewAfterTextChangeEvent;
import com.vicky7230.eatit.data.DataManager;
import com.vicky7230.eatit.data.network.model.recipes.Recipes;
import com.vicky7230.eatit.ui.base.BasePresenter;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;


public class SearchPresenter<V extends SearchMvpView> extends BasePresenter<V> implements SearchMvpPresenter<V> {

    private static final long QUERY_UPDATE_DELAY_MILLIS = 300;

    @Inject
    public SearchPresenter(DataManager dataManager, CompositeDisposable compositeDisposable) {
        super(dataManager, compositeDisposable);
    }

    @Override
    public void instantSearch(InitialValueObservable<TextViewAfterTextChangeEvent> observable) {

        getCompositeDisposable().add((observable)
                .debounce(QUERY_UPDATE_DELAY_MILLIS, TimeUnit.MILLISECONDS)
                .observeOn(Schedulers.io())
                .filter(new Predicate<TextViewAfterTextChangeEvent>() {
                    @Override
                    public boolean test(TextViewAfterTextChangeEvent textViewAfterTextChangeEvent) throws Exception {
                        return textViewAfterTextChangeEvent.editable() != null &&
                                textViewAfterTextChangeEvent.editable().toString().length() > 3;
                    }
                })
                .switchMap(new Function<TextViewAfterTextChangeEvent, ObservableSource<Recipes>>() {
                    @Override
                    public ObservableSource<Recipes> apply(TextViewAfterTextChangeEvent textViewAfterTextChangeEvent) throws Exception {
                        return getDataManager()
                                .searchRecipes(textViewAfterTextChangeEvent.editable().toString(), "1");
                        //.onErrorResumeNext(Observable.<Recipes>empty());
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Recipes>() {
                    @Override
                    public void accept(Recipes recipes) throws Exception {
                        if (recipes != null && recipes.getRecipes() != null) {
                            Timber.d(recipes.getCount().toString());
                            getMvpView().reFreshRecipeList(recipes.getRecipes());
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        getMvpView().showError(throwable.getMessage());
                        Timber.e(throwable);
                    }
                }));
    }
}
