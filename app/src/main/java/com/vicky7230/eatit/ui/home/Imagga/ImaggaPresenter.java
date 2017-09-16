package com.vicky7230.eatit.ui.home.Imagga;

import com.vicky7230.eatit.data.DataManager;
import com.vicky7230.eatit.data.network.model.imagga.content.Content;
import com.vicky7230.eatit.data.network.model.imagga.tag.Tags;
import com.vicky7230.eatit.ui.base.BasePresenter;

import java.io.File;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import timber.log.Timber;

public class ImaggaPresenter<V extends ImaggaMvpView> extends BasePresenter<V> implements ImaggaMvpPresenter<V> {

    @Inject
    public ImaggaPresenter(DataManager dataManager, CompositeDisposable compositeDisposable) {
        super(dataManager, compositeDisposable);
    }

    @Override
    public void uploadImageToImagga(String imagePath) {

        if (imagePath != null) {
            File file = new File(imagePath);

            RequestBody reqFile = RequestBody.create(MediaType.parse("image/jpeg"), file);
            MultipartBody.Part body = MultipartBody.Part.createFormData("upload", file.getName(), reqFile);
            RequestBody name = RequestBody.create(MediaType.parse("text/plain"), "content");

            getMvpView().showLoading();

            getCompositeDisposable().add(getDataManager().uploadImage(name, body)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<Content>() {
                        @Override
                        public void accept(Content content) throws Exception {
                            if (content != null && content.getUploaded() != null)
                                tagImage(content.getUploaded().get(0).getId());
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            getMvpView().hideLoading();
                            Timber.e(throwable);//TODO
                            handleApiError(throwable);
                        }
                    })
            );
        } else {

            getMvpView().showMessage("Bitmap path null.");
        }

    }

    private void tagImage(String content) {

        getCompositeDisposable().add(getDataManager().tagImage(content, 1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Tags>() {
                    @Override
                    public void accept(Tags tags) throws Exception {
                        getMvpView().hideLoading();
                        if (tags != null && tags.getResults() != null) {
                            getMvpView().showMessage(tags.getResults().get(0).getTags().get(0).getTag());
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        getMvpView().hideLoading();
                        Timber.e(throwable);//TODO
                        handleApiError(throwable);
                    }
                }));
    }
}
