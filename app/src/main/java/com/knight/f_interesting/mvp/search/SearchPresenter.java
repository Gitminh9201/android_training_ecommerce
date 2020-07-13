package com.knight.f_interesting.mvp.search;

import android.content.Context;

import com.knight.f_interesting.models.ProductSuggest;
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
    public void onFinished(List<ProductSuggest> suggests) {
        if(view != null){
            view.setDataToView(suggests);
        }
    }

    @Override
    public void onFailure(Throwable throwable) {
        if(view != null){
            view.onResponseFailure(throwable);
        }
    }

    @Override
    public void onDestroy() {
        view = null;
    }

    @Override
    public void resultProducts(String key) {
        Router.navigator(Router.RESULT_PRODUCTS, view.activity(), new String[]{key, null, null});
    }

    @Override
    public void requestSuggest(String key) {
        if(key == null || key.trim().length() == 0){
            model.getLocal(this);
        }
        else
            model.getSuggest(this, key);
    }
}
