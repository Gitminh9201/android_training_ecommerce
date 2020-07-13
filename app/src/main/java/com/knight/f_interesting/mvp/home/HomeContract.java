package com.knight.f_interesting.mvp.home;

import com.knight.f_interesting.models.Banner;
import com.knight.f_interesting.models.Brand;
import com.knight.f_interesting.models.Coupon;
import com.knight.f_interesting.models.Group;

import java.util.List;

public interface HomeContract {
    interface Model {

        interface OnFinishedListener {
            void onFinished(List<Banner> banners, List<Brand> brands, List<Group> groups, List<Coupon> coupons);

            void onFailure(Throwable t);
        }

        void getData(OnFinishedListener onFinishedListener, boolean reload);
    }

    interface View {

        void showProgress();

        void hideProgress();

        void setDataToViews(List<Banner> banners, List<Brand> brands, List<Group> groups, List<Coupon> coupons);

        void onResponseFailure(Throwable throwable);
    }

    interface Presenter {
        void onDestroy();
        void requestData(boolean reload);
    }
}
