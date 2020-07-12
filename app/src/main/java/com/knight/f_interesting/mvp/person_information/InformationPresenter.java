package com.knight.f_interesting.mvp.person_information;

import android.app.Activity;
import android.net.Uri;

import com.knight.f_interesting.models.User;

public class InformationPresenter implements InformationContract.Presenter,
        InformationContract.Model.OnFinishedListener {

    private InformationContract.View view;
    private Activity activity;
    private InformationContract.Model model;

    public InformationPresenter(InformationContract.View view, Activity activity){
        this.view = view;
        this.activity = activity;
        this.model = new InformationModel();
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
            model.getUser(this);
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
    public void updateUser(User user) {
        if(view != null){
            view.showProgress();
            model.updateUser(this, user);
            activity.finish();
        }
    }

    @Override
    public void onDestroy() {
        view = null;
    }
}
