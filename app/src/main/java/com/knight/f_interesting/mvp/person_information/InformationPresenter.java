package com.knight.f_interesting.mvp.person_information;

import android.content.Context;

import com.knight.f_interesting.models.User;

public class InformationPresenter implements InformationContract.Presenter,
        InformationContract.Model.OnFinishedListener {

    private InformationContract.View view;
    private InformationContract.Model model;
    private Context context;

    public InformationPresenter(Context context, InformationContract.View view){
        this.view = view;
        this.context = context;
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
            model.getUser(this, context);
        }
    }

    @Override
    public void updateData(User user) {
        if(view != null){
            view.showProgress();
            model.updateUser(user);
        }
    }

    @Override
    public void onDestroy() {
        view = null;
    }
}
