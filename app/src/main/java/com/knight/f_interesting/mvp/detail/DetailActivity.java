package com.knight.f_interesting.mvp.detail;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;
import com.knight.f_interesting.R;
import com.knight.f_interesting.adapters.GalleryDetailAdapter;
import com.knight.f_interesting.adapters.ProductItemAdapter;
import com.knight.f_interesting.api.Client;
import com.knight.f_interesting.buses.UserBus;
import com.knight.f_interesting.models.Gallery;
import com.knight.f_interesting.models.Product;
import com.knight.f_interesting.dialogs.RequestLogin;
import com.knight.f_interesting.utils.AppUtils;
import com.knight.f_interesting.utils.Router;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity implements DetailContract.View {

    private int id;

    private LinearLayout llLoading;
    private ViewPager vpGallery;
    private TextView txtTitle;
    private TextView txtPrice;
    private TextView txtContent;
    private TextView txtPriceCompare;
    private ImageButton ibBack;
    private ImageButton ibCart;
    private ImageButton ibAddCart;
    private TextView txtTitleBrand;
    private TextView txtDesBrand;
    private ImageView ivBrand;
    private TextView txtCountryBrand;
    private RecyclerView rvRelated;
    private TextView txtRelated;
    private ProductItemAdapter relatedAdapter;
    private TextView txtBuyNow;
    private ImageButton ibAddBookMark;

    private List<Product> related;
    private List<Gallery> gallery;
    private Product product;

    private GalleryDetailAdapter galleryAdapter;
    private DetailPresenter presenter;

    private void init() {
        llLoading = findViewById(R.id.ll_load_detail);
        vpGallery = findViewById(R.id.vp_gallery_detail);
        txtPrice = findViewById(R.id.txt_price_detail);
        ibAddCart = findViewById(R.id.ib_add_cart);
        ibCart = findViewById(R.id.ib_cart);
        txtTitle = findViewById(R.id.txt_title_detail);
        txtPriceCompare = findViewById(R.id.txt_price_compare_detail);
        ibBack = findViewById(R.id.ib_back);
        txtRelated = findViewById(R.id.txt_title_group);
        txtRelated.setText("Sản phẩm liên quan:");
        rvRelated = findViewById(R.id.rv_group_product);
        txtContent = findViewById(R.id.txt_content_detail);
        txtCountryBrand = findViewById(R.id.txt_country_brand);
        txtTitleBrand = findViewById(R.id.txt_title_brand_detail);
        txtDesBrand = findViewById(R.id.txt_description_brand);
        ivBrand = findViewById(R.id.iv_image_brand);
        txtBuyNow = findViewById(R.id.txt_buy_now);
        ibAddBookMark = findViewById(R.id.ib_add_bookmark);

        gallery = new ArrayList<>();
        related = new ArrayList<>();
        product = new Product();
        id = Router.inDetail(this);

        relatedAdapter = new ProductItemAdapter(related, getApplicationContext(), true);
        LinearLayoutManager llmanager = new LinearLayoutManager(getApplicationContext());
        llmanager.setOrientation(RecyclerView.HORIZONTAL);
        rvRelated.setLayoutManager(llmanager);
        rvRelated.setAdapter(relatedAdapter);
        galleryAdapter = new GalleryDetailAdapter(getApplicationContext(), gallery);
        vpGallery.setAdapter(galleryAdapter);
        presenter = new DetailPresenter(this, getApplicationContext());
        presenter.requestData(id);
    }

    private void listener(final Activity activity) {
        ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ibCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Router.goToCart(DetailActivity.this);
            }
        });
        ibAddCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppUtils.db.addCart(product.getId(), 1);
                Toast.makeText(getApplicationContext(), getResources()
                        .getString(R.string.add_cart_success), Toast.LENGTH_LONG)
                        .show();
            }
        });
        txtBuyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppUtils.db.addCart(product.getId(), 1);
                Router.goToCart(DetailActivity.this);
            }
        });
        ibAddBookMark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(UserBus.current() != null && UserBus.current().getName() != null){
                    presenter.changeBookmark(id);
                }
                else{
                    DialogFragment dialogFragment = new RequestLogin(getResources()
                            .getString(R.string.request_login_input_collection), activity);
                    dialogFragment.show(getSupportFragmentManager().beginTransaction(), "dialog");
                }
            }
        });
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        init();
        listener(this);
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
    public void setButtonBookmark(boolean status) {
        if(status){
            ibAddBookMark.setImageResource(R.drawable.ic_bookmark_added);
        }
        else{
            ibAddBookMark.setImageResource(R.drawable.ic_bookmark);
        }
    }

    @Override
    public void setDataToView(Product product) {
        if (!product.getGallery().isEmpty()) this.gallery = product.getGallery();
        this.product = product;
        this.related = product.getRelated();
        this.gallery.add(new Gallery(0, product.getImage(), 0, product.getId()));
        relatedAdapter.changeData(this.related);
        galleryAdapter.changeData(gallery);
        txtContent.setText(product.getContent());
        txtDesBrand.setText(product.getBrand().getDescription());
        txtTitleBrand.setText(product.getBrand().getTitle());
        txtCountryBrand.setText(product.getBrand().getCountry());
        Glide.with(this).load(Client.url() + product.getBrand().getImage()).into(ivBrand);
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