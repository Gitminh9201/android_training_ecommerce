package com.knight.f_interesting.mvp.detail;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.knight.f_interesting.R;
import com.knight.f_interesting.adapters.GalleryDetailAdapter;
import com.knight.f_interesting.models.Gallery;
import com.knight.f_interesting.models.Product;
import com.knight.f_interesting.utils.AppUtils;
import com.knight.f_interesting.utils.Route;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity implements DetailContract.View {

    private int id;

    private LinearLayout llLoading;
    private ViewPager vpGallery;
    private TextView txtTitle;
    private TextView txtPrice;
    private TextView txtPriceCompare;
    private ImageButton ibBack;

    private List<Gallery> gallery;
    private Product product;

    private GalleryDetailAdapter galleryAdapter;
    private DetailPresenter presenter;

    private void init() {
        llLoading = findViewById(R.id.ll_load_detail);
        vpGallery = findViewById(R.id.vp_gallery_detail);
        txtPrice = findViewById(R.id.txt_price_detail);
        txtTitle = findViewById(R.id.txt_title_detail);
        txtPriceCompare = findViewById(R.id.txt_price_compare_detail);
        ibBack = findViewById(R.id.ib_back);

        gallery = new ArrayList<>();
        product = new Product();
        id = Route.inDetail(this);

        galleryAdapter = new GalleryDetailAdapter(getApplicationContext(), gallery);
        vpGallery.setAdapter(galleryAdapter);
        presenter = new DetailPresenter(this);
        presenter.requestData(id);
    }

    private void listener(){
        ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        init();
        listener();
    }

    @Override
    public void showProgress() {
        llLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        llLoading.setVisibility(View.GONE);
    }

    @Override
    public void setDataToView(Product product) {
        if(!product.getGallery().isEmpty()) this.gallery = product.getGallery();
        this.product = product;
        galleryAdapter.changeData(gallery);
        txtPriceCompare.setText(AppUtils.currencyVN(product.getPriceCompare()));
        txtPrice.setText(AppUtils.currencyVN(product.getPrice()));
        txtTitle.setText(product.getTitle());
    }

    @Override
    public void onResponseFailure(Throwable throwable) {
        Snackbar.make(findViewById(R.id.layout_detail), getString(R.string.error_data),
                Snackbar.LENGTH_LONG).show();
    }
}