package com.knight.clothes.mvp.products;

import com.knight.clothes.models.Product;

import java.util.List;

public class ProductsPresenter implements ProductsContract.Presenter, ProductsContract.Model.OnFinishedListener {

    private ProductsContract.View view;
    private ProductsContract.Model model;

    public ProductsPresenter(ProductsContract.View view) {
        this.view = view;
        this.model = new ProductsModel();
    }

    @Override
    public void onFinished(List<Product> products) {
        if(view != null){
            view.hideProgress();
            view.setDataToView(products);
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
        view = null;
    }

    @Override
    public void requestData(String keyword, int categoryId, int brandId, int groupId, int offset, int limit, int sort) {
        if (view != null){
            view.showProgress();
            model.getData(this, keyword, categoryId, brandId, groupId, offset, limit, sort);
        }
    }
}
