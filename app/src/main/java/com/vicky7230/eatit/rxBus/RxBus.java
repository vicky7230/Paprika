package com.vicky7230.eatit.rxBus;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.subjects.PublishSubject;

public final class RxBus {

    private static PublishSubject<Object> publishSubject = PublishSubject.create();

    private RxBus() {
        // hidden constructor
    }

    public static Disposable subscribe(@NonNull Consumer<Object> action, Consumer<Throwable> throwableConsumer) {
        return publishSubject.subscribe(action, throwableConsumer);
    }

    public static void publish(@NonNull Object message) {
        publishSubject.onNext(message);
    }
}

