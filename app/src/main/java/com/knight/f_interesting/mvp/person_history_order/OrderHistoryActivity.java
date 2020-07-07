package com.knight.f_interesting.mvp.person_history_order;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.knight.f_interesting.R;
import com.knight.f_interesting.adapters.OrderHistoryAdapter;
import com.knight.f_interesting.base.BaseView;
import com.knight.f_interesting.buses.CartBus;
import com.knight.f_interesting.models.Cart;
import com.knight.f_interesting.models.Order;
import com.knight.f_interesting.utils.AppUtils;
import com.knight.f_interesting.utils.Router;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.functions.Consumer;

public class OrderHistoryActivity extends AppCompatActivity implements BaseView.BaseActivity,
        OrderHistoryContract.View {

    private LinearLayout llLoading;
    private RecyclerView rvOrders;
    private ImageButton ibCart;
    private TextView txtBadge;
    private ImageButton ibSearch;
    private ImageButton ibBack;

    private List<Order> orders;
    private OrderHistoryAdapter adapter;

    private OrderHistoryContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);
        init();
        refreshCart();
        listener(this);
    }

    @Override
    public void init() {
        ibSearch = findViewById(R.id.ib_search);
        ibBack = findViewById(R.id.ib_back);
        txtBadge = findViewById(R.id.txt_badge_cart_toolbar);
        llLoading = findViewById(R.id.ll_load_history);
        rvOrders = findViewById(R.id.rv_history_order);
        ibCart = findViewById(R.id.ib_cart_toolbar);

        orders = new ArrayList<>();
        adapter = new OrderHistoryAdapter(getApplicationContext(), orders);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        rvOrders.setLayoutManager(linearLayoutManager);
        rvOrders.setAdapter(adapter);

        presenter = new OrderHistoryPresenter(this, getApplicationContext());
        presenter.requestData();
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

    @Override
    public void listener(final Activity activity) {
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
        ibSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Router.navigator(Router.SEARCH, activity, null);
            }
        });
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
    public void setData(List<Order> orders) {
        if(orders != null && orders.size() > 0){
            adapter.refresh(orders);
            this.orders = orders;
        }
        else {
            AppUtils.showToast(getResources().getString(R.string.empty_data), getApplicationContext());
        }
    }

    @Override
    public void onResponseFailure(Throwable throwable) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }
}