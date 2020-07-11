package com.knight.f_interesting.mvp.result_products;

import com.knight.f_interesting.models.Product;
import com.knight.f_interesting.utils.Router;

import java.util.List;

public class ResultProductsPresenter implements ResultProductsContract.Presenter, ResultProductsContract.Model.OnFinishedListener {
    private ResultProductsContract.View view;
    private ResultProductsContract.Model model;

    public ResultProductsPresenter(ResultProductsContract.View view) {
        this.view = view;
        this.model = new ResultProductsModel();
    }

    @Override
    public void onFinished(List<Product> products) {
        if (view != null) {
            view.hideProgress();
            view.setDataToView(products);
        }
    }

    @Override
    public void onFailure(Throwable throwable) {
        if (view != null) {
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
        if (view != null) {
            view.showProgress();
            model.getData(this, keyword, categoryId, brandId, groupId, offset, limit, sort);
        }
    }
    @Override
    public void requestData(String keyword) {
        if (view != null) {
            view.showProgress();
            model.getData(this, keyword);
        }
    }

    @Override
    public void refresh(String keyword) {
        Router.navigator(Router.RESULT_PRODUCTS, view.activity(), new String[]{keyword});
    }
}
