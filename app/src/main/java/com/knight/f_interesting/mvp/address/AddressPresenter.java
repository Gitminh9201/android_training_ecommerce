package com.knight.f_interesting.mvp.address;

import android.content.Context;
import android.util.Log;

import com.knight.f_interesting.models.Address;

public class AddressPresenter implements AddressContract.Presenter, AddressContract.Model.OnFinishedListener {

    private AddressContract.Model model;
    private AddressContract.View view;
    private Context context;

    public AddressPresenter(AddressContract.View view, Context context){
        this.context = context;
        this.view = view;
        this.model = new AddressModel();
    }

    @Override
    public void onFinished(Address data) {
        if(view != null){
            view.hideProgress();
            view.setData(data);
        }
    }

    @Override
    public void onFailure(Throwable th) {
        if(view != null){
            view.hideProgress();
            view.onResponseFailure(th);
        }
    }

    @Override
    public void onDestroy() {
        view = null;
    }

    @Override
    public void requestData() {
        if(view != null){
            view.showProgress();
        }
        Log.d("Data shared", "request");
        model.getData(this, context);
    }
}
