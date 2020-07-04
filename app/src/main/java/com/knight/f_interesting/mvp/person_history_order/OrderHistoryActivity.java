package com.knight.f_interesting.mvp.person_history_order;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.knight.f_interesting.R;
import com.knight.f_interesting.adapters.OrderHistoryAdapter;
import com.knight.f_interesting.base.BaseView;
import com.knight.f_interesting.models.Order;
import com.knight.f_interesting.utils.AppUtils;

import java.util.ArrayList;
import java.util.List;

public class OrderHistoryActivity extends AppCompatActivity implements BaseView.BaseActivity,
        OrderHistoryContract.View {

    private LinearLayout llLoading;
    private RecyclerView rvOrders;

    private List<Order> orders;
    private OrderHistoryAdapter adapter;

    private OrderHistoryContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);
        init();
        listener(this);
    }

    @Override
    public void init() {
        llLoading = findViewById(R.id.ll_load_history);
        rvOrders = findViewById(R.id.rv_history_order);

        orders = new ArrayList<>();
        adapter = new OrderHistoryAdapter(getApplicationContext(), orders);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        rvOrders.setLayoutManager(linearLayoutManager);
        rvOrders.setAdapter(adapter);

        presenter = new OrderHistoryPresenter(this, getApplicationContext());
        presenter.requestData();
    }

    @Override
    public void listener(Activity activity) {

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
            AppUtils.showToast(getResources().getString(R.string.error_data), getApplicationContext());
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