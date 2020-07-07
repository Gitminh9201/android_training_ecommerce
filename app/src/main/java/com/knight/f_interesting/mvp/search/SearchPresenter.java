package com.knight.f_interesting.mvp.search;

import android.content.Context;

import com.knight.f_interesting.utils.Router;

import java.util.List;

public class SearchPresenter implements SearchContract.Presenter, SearchContract.Model.OnFinishedListener {

    private SearchContract.View view;
    private SearchContract.Model model;
    private Context context;

    public SearchPresenter(SearchContract.View view, Context context){
        this.context = context;
        this.view = view;
        this.model = new SearchModel();
    }

    @Override
    public void onFinished(List<String> strings) {
        if(view != null){
            view.hideProgress();
            view.setDataToView(strings);
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
    public void resultProducts(String key) {
        if(view != null){
            view.hideProgress();
            Router.navigator(Router.RESULT_PRODUCTS, view.activity(), null);
        }
    }

    @Override
    public void requestData() {

    }
}
