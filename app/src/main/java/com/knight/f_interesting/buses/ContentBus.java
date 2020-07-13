package com.knight.f_interesting.buses;

import com.knight.f_interesting.models.Banner;
import com.knight.f_interesting.models.Brand;
import com.knight.f_interesting.models.Category;
import com.knight.f_interesting.models.Coupon;
import com.knight.f_interesting.models.Group;

import java.util.List;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.annotations.Nullable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.subjects.BehaviorSubject;

public class ContentBus {

    private static final BehaviorSubject<List<Group>> behaviorSubjectGroup
            = BehaviorSubject.create();
    private static final BehaviorSubject<List<Coupon>> behaviorSubjectCoupon
            = BehaviorSubject.create();
    private static final BehaviorSubject<List<Category>> behaviorSubjectCategory
            = BehaviorSubject.create();
    private static final BehaviorSubject<List<Brand>> behaviorSubjectBrand
            = BehaviorSubject.create();
    private static final BehaviorSubject<List<Banner>> behaviorSubjectBanner
            = BehaviorSubject.create();

    public static Disposable subscribeGroup(@Nullable Consumer<List<Group>> action){
        return behaviorSubjectGroup.subscribe(action);
    }
    public static Disposable subscribeCoupon(@NonNull Consumer<List<Coupon>> action) {
        return behaviorSubjectCoupon.subscribe(action);
    }
    public static Disposable subscribeCategory(@NonNull Consumer<List<Category>> action) {
        return behaviorSubjectCategory.subscribe(action);
    }
    public static Disposable subscribeBrand(@NonNull Consumer<List<Brand>> action) {
        return behaviorSubjectBrand.subscribe(action);
    }
    public static Disposable subscribeBanner(@NonNull Consumer<List<Banner>> action) {
        return behaviorSubjectBanner.subscribe(action);
    }

    public static List<Group> groups(){
        return behaviorSubjectGroup.getValue();
    }
    public static List<Coupon> coupons(){
        return behaviorSubjectCoupon.getValue();
    }
    public static List<Category> categories(){
        return behaviorSubjectCategory.getValue();
    }
    public static List<Brand> brands(){
        return behaviorSubjectBrand.getValue();
    }
    public static List<Banner> banners(){
        return behaviorSubjectBanner.getValue();
    }

    public static void publishGroup(List<Group> groups){
        behaviorSubjectGroup.onNext(groups);
    }
    public static void publishCoupon(List<Coupon> coupons){
        behaviorSubjectCoupon.onNext(coupons);
    }
    public static void publishCategory(List<Category> categories){
        behaviorSubjectCategory.onNext(categories);
    }
    public static void publishBrand(List<Brand> brands){
        behaviorSubjectBrand.onNext(brands);
    }
    public static void publishBanner(List<Banner> banners){
        behaviorSubjectBanner.onNext(banners);
    }
}
