package com.knight.clothes.mvp.detail_invoice;

import com.knight.clothes.models.Invoice;

public class DetailInvoicePresenter implements DetailInvoiceContract.Presenter, DetailInvoiceContract.Model.OnFinishedListener{

    private DetailInvoiceContract.View view;
    private DetailInvoiceContract.Model model;

    public DetailInvoicePresenter(DetailInvoiceContract.View view){
        this.view = view;
        this.model = new DetailInvoiceModel();
    }

    @Override
    public void onCancelSuccess() {
        if(view != null){
            view.hideProgress();
            view.cancelFinish();
        }
    }

    @Override
    public void onGetDataFinished(Invoice invoice) {
        if(view != null){
            view.hideProgress();
            view.setDataToView(invoice);
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
    public void onDestroy() {
        if(view != null){
            view = null;
        }
    }

    @Override
    public void cancelInvoice(int id) {
        if(view != null){
            view.showProgress();
            model.cancelInvoice(this, id);
        }
    }

    @Override
    public void requestData(int id) {
        if(view != null){
            view.showProgress();
            model.getData(this, id);
        }
    }
}
