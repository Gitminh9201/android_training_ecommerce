package com.knight.clothes.mvp.person;

import android.content.Context;
import android.net.Uri;

import com.knight.clothes.buses.UserBus;
import com.knight.clothes.models.User;
import com.knight.clothes.utils.AppShared;

public class PersonPresenter implements PersonContract.Presenter, PersonContract.Model.OnFinishedListener {

    private PersonContract.View view;
    private Context context;
    private PersonContract.Model model;

    public PersonPresenter(Context context, PersonContract.View view){
        this.context = context;
        this.view  =view;
        this.model = new PersonModel();
    }

    @Override
    public void onFinish(User user) {
        if(view != null){
            view.hideProgress();
            view.setData(user);
        }
    }

    @Override
    public void onFailure(Throwable throwable) {
        if(view != null){
            view.hideProgress();
            view.onResponseFailure(throwable);
        }
    }

    @Override
    public void requestData() {
        if(view != null){
            view.showProgress();
            model.getUser(this, context);
        }
    }

    @Override
    public void uploadAvatar(Uri uri) {
        if(view != null){
            view.showProgress();
            model.uploadAvatar(this, uri);
        }
    }

    @Override
    public void logout() {
        AppShared.setAccessToken(context, null);
        UserBus.publish(new User());
        requestData();
    }

    @Override
    public void onDestroy() {
        view = null;
    }
}
