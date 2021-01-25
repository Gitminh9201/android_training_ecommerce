package com.knight.clothes.mvp.detail_product;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;
import com.knight.clothes.R;
import com.knight.clothes.adapters.GalleryDetailAdapter;
import com.knight.clothes.adapters.GalleryItemAdapterDetail;
import com.knight.clothes.adapters.ProductItemAdapter;
import com.knight.clothes.api.AppClient;
import com.knight.clothes.buses.CartBus;
import com.knight.clothes.customs.RecyclerItemClickListener;
import com.knight.clothes.models.Cart;
import com.knight.clothes.models.Gallery;
import com.knight.clothes.models.Product;
import com.knight.clothes.utils.AppSizes;
import com.knight.clothes.utils.AppUtils;
import com.knight.clothes.utils.Router;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.functions.Consumer;

public class DetailProductActivity extends AppCompatActivity implements DetailProductContract.View {

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
    private TextView txtBadge;
    private TextView txtTitlePage;
    private ImageButton ibSearch;
    private RecyclerView rvGallery;

    private List<Product> related;
    private List<Gallery> gallery;
    private Product product;

    private GalleryItemAdapterDetail galleryItemAdapter;
    private GalleryDetailAdapter galleryAdapter;
    private DetailProductPresenter presenter;

    private Intent intent;

    private void init(Activity activity) {
        ibSearch = findViewById(R.id.ib_search);
        txtTitlePage = findViewById(R.id.txt_title_toolbar);
        txtTitlePage.setText(R.string.page_detail_product);
        txtBadge = findViewById(R.id.txt_badge_cart_toolbar);
        llLoading = findViewById(R.id.ll_load_detail);
        vpGallery = findViewById(R.id.vp_gallery_detail);
        vpGallery.setLayoutParams(new FrameLayout.LayoutParams(AppSizes.getScreenWidth(), AppSizes.getScreenWidth()));
        rvGallery = findViewById(R.id.rv_gallery_detail);
        txtPrice = findViewById(R.id.txt_price_detail);
        ibAddCart = findViewById(R.id.ib_add_cart);
        ibCart = findViewById(R.id.ib_cart_toolbar);
        txtTitle = findViewById(R.id.txt_title_detail);
        txtPriceCompare = findViewById(R.id.txt_price_compare_detail);
        ibBack = findViewById(R.id.ib_back);
        txtRelated = findViewById(R.id.txt_title_group);
        txtRelated.setText(R.string.product_related);
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

        intent = activity.getIntent();
        id = intent.getIntExtra("id", 1);

        galleryItemAdapter = new GalleryItemAdapterDetail(gallery);
        relatedAdapter = new ProductItemAdapter(related, getApplicationContext(), true);
        LinearLayoutManager llManagerGallery = new LinearLayoutManager(getApplicationContext());
        llManagerGallery.setOrientation(RecyclerView.HORIZONTAL);
        rvGallery.setLayoutManager(llManagerGallery);
        rvGallery.setAdapter(galleryItemAdapter);
        LinearLayoutManager llManagerRelated = new LinearLayoutManager(getApplicationContext());
        llManagerRelated.setOrientation(RecyclerView.HORIZONTAL);
        rvRelated.setLayoutManager(llManagerRelated);
        rvRelated.setAdapter(relatedAdapter);
        galleryAdapter = new GalleryDetailAdapter(getApplicationContext(), gallery);
        vpGallery.setAdapter(galleryAdapter);
        presenter = new DetailProductPresenter(this, getApplicationContext());
        presenter.requestData(id);
    }

    private void refreshCart() {
        CartBus.subscribe(new Consumer<List<Cart>>() {
            @Override
            public void accept(List<Cart> carts) throws Throwable {
                if(carts.size() != 0){
                    txtBadge.setVisibility(View.VISIBLE);
                    txtBadge.setText(String.valueOf(carts.size()));
                }else
                    txtBadge.setVisibility(View.GONE);
            }
        });
    }

    private void listener(final Activity activity) {
        ibSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Router.navigator(Router.SEARCH, activity, null);
            }
        });
        ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ibCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Router.navigator(Router.CART, activity, null);
            }
        });
        ibAddCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppUtils.db.addCart(product.getId(), 1);
                CartBus.refresh();
                Toast.makeText(getApplicationContext(), getResources()
                        .getString(R.string.add_cart_success), Toast.LENGTH_LONG)
                        .show();
            }
        });
        txtBuyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppUtils.db.addCart(product.getId(), 1);
                CartBus.refresh();
                Router.navigator(Router.CART, activity, null);
            }
        });
        ibAddBookMark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AppUtils.logged()) {
                    presenter.changeBookmark(id);
                } else {
                    AppUtils.showDialogAuth(getResources()
                            .getString(R.string.request_login_input_collection),
                            activity, getSupportFragmentManager());
                }
            }
        });
        rvGallery.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(),
                rvGallery, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                vpGallery.setCurrentItem(position);
                galleryItemAdapter.changeData(position);
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));
        vpGallery.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                galleryItemAdapter.changeData(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product);
        init(this);
        refreshCart();
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
        if (status) {
            ibAddBookMark.setImageResource(R.drawable.ic_bookmark_added);
        } else {
            ibAddBookMark.setImageResource(R.drawable.ic_bookmark);
        }
    }

    @Override
    public void setDataToView(Product product) {
        if (!product.getGallery().isEmpty()) this.gallery = product.getGallery();
        else this.gallery.add(new Gallery(0, product.getImage(), 0, product.getId()));
        this.product = product;
        this.related = product.getRelated();
        relatedAdapter.changeData(this.related);
        galleryAdapter.changeData(gallery);
        galleryItemAdapter.changeData(gallery);
        txtContent.setText(product.getContent());
        txtDesBrand.setText(product.getBrand().getDescription());
        txtTitleBrand.setText(product.getBrand().getTitle());
        txtCountryBrand.setText(product.getBrand().getCountry());
        Glide.with(this).load(AppClient.url() + product.getBrand().getImage()).into(ivBrand);
        txtPriceCompare.setText(AppUtils.currencyVN(product.getPriceCompare()));
        txtPrice.setText(AppUtils.currencyVN(product.getPrice()));
        txtTitle.setText(product.getTitle());
    }

    @Override
    public void onResponseFailure(Throwable throwable) {
        Snackbar.make(findViewById(R.id.layout_detail), getString(R.string.error_data),
                Snackbar.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }
}