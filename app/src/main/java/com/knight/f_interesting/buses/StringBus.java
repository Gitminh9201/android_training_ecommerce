package com.knight.f_interesting.buses;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.subjects.BehaviorSubject;

public class StringBus {
    private static final BehaviorSubject<String> behaviorSubject
            = BehaviorSubject.create();
    public static Disposable subscribe(@NonNull Consumer<String> action){
        return behaviorSubject.subscribe(action);
    }
    public static void publish(String msg){
        behaviorSubject.onNext(msg);
    }
}
