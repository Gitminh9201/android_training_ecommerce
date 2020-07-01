package com.knight.f_interesting.mvp.person_information;

import android.content.Context;

import com.knight.f_interesting.models.User;

public interface InformationContract {
    interface View{
        void showProgress();
        void hideProgress();
        void setData(User user);
        void init();
        void listener();
        void onResponseFailure(Throwable throwable);
    }
    interface Model{
        interface OnFinishedListener{
            void onFinish(User user);
            void onFailure(Throwable throwable);
        }
        void getUser(OnFinishedListener onFinishedListener, Context context);
        void updateUser(User user);
    }
    interface Presenter{
        void requestData();
        void updateData(User user);
        void onDestroy();
    }
}
