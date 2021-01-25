package com.knight.clothes.buses;

import android.content.Context;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.subjects.BehaviorSubject;

public class ContextBus {
    private static final BehaviorSubject<Context> behaviorSubject
            = BehaviorSubject.create();
    public static Disposable subscribe(@NonNull Consumer<Context> action) {
        return behaviorSubject.subscribe(action);
    }
    public static Context current(){
        return behaviorSubject.getValue();
    }
    public static void publish(Context context){
        behaviorSubject.onNext(context);
    }
}
