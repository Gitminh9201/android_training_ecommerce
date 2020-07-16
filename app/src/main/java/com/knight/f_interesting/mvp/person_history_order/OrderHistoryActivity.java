package com.knight.f_interesting.mvp.person_history_order;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.knight.f_interesting.R;
import com.knight.f_interesting.adapters.InvoicesAdapter;
import com.knight.f_interesting.adapters.loadmores.LoadMoreInvoices;
import com.knight.f_interesting.base.BaseView;
import com.knight.f_interesting.models.Order;
import com.knight.f_interesting.utils.AppUtils;

import java.util.ArrayList;
import java.util.List;

public class OrderHistoryActivity extends AppCompatActivity implements BaseView.BaseActivity,
        OrderHistoryContract.View {

    private LinearLayout llLoading;
    private RecyclerView rvOrders;
    private ImageButton ibBack;
    private TextView txtTitle;

    private List<Order> orders;
//    private OrderHistoryAdapter adapter;
    private InvoicesAdapter adapter;

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
        txtTitle = findViewById(R.id.txt_title_toolbar);
        txtTitle.setText(R.string.page_history_order);
        ibBack = findViewById(R.id.ib_back);
        llLoading = findViewById(R.id.ll_load_history);
        rvOrders = findViewById(R.id.rv_history_order);

        orders = new ArrayList<>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        rvOrders.setLayoutManager(linearLayoutManager);
        adapter = new InvoicesAdapter(this, orders, rvOrders, new LoadMoreInvoices() {
            @Override
            public void onLoadMore(int offset, int limit) {
                orders.add(null);
                adapter.notifyItemInserted(orders.size()-1);
                presenter.requestData(offset, limit);
            }
        });
        rvOrders.setAdapter(adapter);

        presenter = new OrderHistoryPresenter(this, getApplicationContext());
        presenter.requestData();
    }

    @Override
    public void listener(final Activity activity) {
        ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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
    public void setMoreData(final List<Order> invoices) {
        if(!invoices.isEmpty()){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    orders.remove(orders.size()-1);
                    adapter.notifyItemRemoved(orders.size());
                    adapter.setLoaded();
                    orders.addAll(invoices);
                    adapter.refresh(orders);
                }
            }, 1000);
        }
        else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    orders.remove(orders.size()-1);
                    adapter.notifyItemRemoved(orders.size());
                    AppUtils.showToast(R.string.loadCompleted, getApplicationContext());
                }
            }, 1500);
        }
    }

    @Override
    public void setDataOriginal(List<Order> orders) {
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