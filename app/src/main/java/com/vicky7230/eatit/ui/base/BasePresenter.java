package com.vicky7230.eatit.ui.base;

import com.vicky7230.eatit.data.DataManager;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import timber.log.Timber;

/**
 * Created by agrim on 4/7/17.
 */

public class BasePresenter<V extends MvpView> implements MvpPresenter<V> {

    private static final String TAG = BasePresenter.class.getSimpleName();

    private final DataManager dataManager;
    private final CompositeDisposable compositeDisposable;

    private V mvpView;

    @Inject
    public BasePresenter(DataManager dataManager, CompositeDisposable compositeDisposable) {
        this.dataManager = dataManager;
        this.compositeDisposable = compositeDisposable;
    }

    @Override
    public void onAttach(V mvpView) {
        this.mvpView = mvpView;
    }

    @Override
    public void onDetach() {
        compositeDisposable.dispose();
        this.mvpView = null;
    }

    public boolean isViewAttached() {
        return mvpView != null;
    }

    public V getMvpView() {
        return mvpView;
    }

    public void checkViewAttached() {
        if (!isViewAttached()) throw new MvpViewNotAttachedException();
    }

    public DataManager getDataManager() {
        return dataManager;
    }

    public CompositeDisposable getCompositeDisposable() {
        return compositeDisposable;
    }

    public static class MvpViewNotAttachedException extends RuntimeException {
        public MvpViewNotAttachedException() {
            super("Please call Presenter.onAttach(MvpView) before" +
                    " requesting data to the Presenter");
        }
    }

    @Override
    public void handleApiError(Throwable throwable) {

        Timber.e(throwable);

        /*String errorMessage;

        if (throwable instanceof HttpException) {

            HttpException httpException = (HttpException) throwable;

            switch (httpException.code()) {

                case 504:
                    errorMessage = "Network Error";
                    break;

                default:
                    errorMessage = "Some network Error";
                    break;
            }

        } else if (throwable instanceof SocketTimeoutException) {

            errorMessage = "Connection timed out";

        } else if (throwable instanceof IOException) {

            errorMessage = "Network Error";

        } else {

            errorMessage = "Some Error";
        }*/

        getMvpView().showError(throwable.getMessage());

    }

}
