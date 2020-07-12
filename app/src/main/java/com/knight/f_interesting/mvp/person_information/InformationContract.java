package com.knight.f_interesting.mvp.person_information;

import android.net.Uri;

import com.knight.f_interesting.models.User;

public interface InformationContract {
    interface View{
        void showProgress();
        void hideProgress();
        void setData(User user);
        void onResponseFailure(Throwable throwable);
    }
    interface Model{
        interface OnFinishedListener{
            void onFinish(User user);
            void onFailure(Throwable throwable);
        }
        void getUser(OnFinishedListener onFinishedListener);
        void updateUser(OnFinishedListener onFinishedListener, User user);
        void uploadAvatar(OnFinishedListener onFinishedListener, Uri uri);
    }
    interface Presenter{
        void requestData();
        void updateUser(User user);
        void uploadAvatar(Uri uri);
        void onDestroy();
    }
}
