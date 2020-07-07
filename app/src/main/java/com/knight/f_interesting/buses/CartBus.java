package com.knight.f_interesting.buses;

import com.knight.f_interesting.models.Cart;
import com.knight.f_interesting.utils.AppUtils;

import java.util.List;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.subjects.BehaviorSubject;

public class CartBus {
    private static final BehaviorSubject<List<Cart>> behaviorSubject
            = BehaviorSubject.create();
    public static void refresh(){
        List<Cart> carts = AppUtils.db.getCart();
        CartBus.publish(carts);
    }
    public static Disposable subscribe(@NonNull Consumer<List<Cart>> action) {
        return behaviorSubject.subscribe(action);
    }
    public static List<Cart> current(){
        return behaviorSubject.getValue();
    }
    public static void publish(List<Cart> carts){
        behaviorSubject.onNext(carts);
    }
}
