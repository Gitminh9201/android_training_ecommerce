package com.knight.clothes.mvp.invoices;

import android.content.Context;

import com.knight.clothes.models.Invoice;

import java.util.List;

public class InvoicesPresenter implements InvoicesContract.Presenter, InvoicesContract.Model.OnFinishedListener {

    private InvoicesContract.View view;
    private InvoicesContract.Model model;

    public InvoicesPresenter(InvoicesContract.View view, Context context){
        this.view = view;
        this.model = new InvoicesModel();
    }

    @Override
    public void onLoadMoreFinished(List<Invoice> invoices) {
        if(view != null)
            view.setMoreData(invoices);
    }

    @Override
    public void onGetDataFinished(List<Invoice> invoices) {
        if(view != null){
            view.hideProgress();
            view.setDataOriginal(invoices);
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
            model.getData(this);
        }
    }

    @Override
    public void requestData(int offset) {
        if(view != null){
            model.getData(this, offset);
        }
    }

    @Override
    public void requestData(int offset, int limit) {
        if(view != null){
            model.getData(this, offset, limit);
        }
    }

    @Override
    public void onDestroy() {
        view = null;
    }
}
