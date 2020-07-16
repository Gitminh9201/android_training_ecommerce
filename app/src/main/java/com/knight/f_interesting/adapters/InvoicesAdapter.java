package com.knight.f_interesting.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.knight.f_interesting.R;
import com.knight.f_interesting.adapters.loadmores.LoadMoreInvoices;
import com.knight.f_interesting.models.Order;
import com.knight.f_interesting.utils.AppContracts;
import com.knight.f_interesting.utils.AppSizes;
import com.knight.f_interesting.utils.Router;

import java.util.List;

public class InvoicesAdapter extends RecyclerView.Adapter{

    private List<Order> orders;
    private Activity activity;

    private final int TYPE_LOADING = 0, TYPE_ITEM = 1;
    private final int PAGINATE = 10;

    private boolean isLoading;
    private int lastVisible;
    private int totalVisible;

    public InvoicesAdapter(Activity activity, List<Order> orders, RecyclerView rv, final LoadMoreInvoices loadMore){
        this.orders = orders;
        this.activity = activity;
        final GridLayoutManager layoutManager = (GridLayoutManager)rv.getLayoutManager();
        rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            /**
             * Callback method to be invoked when the RecyclerView has been scrolled. This will be
             * called after the scroll has completed.
             * This callback will also be called if visible item range changes after a layout
             * calculation. In that case, dx and dy will be 0.
             * @param recyclerView The RecyclerView which scrolled.
             * @param dx           The amount of horizontal scroll.
             * @param dy           The amount of vertical scroll.
             */
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                loadMore.onLoadMore(layoutManager.getItemCount(), layoutManager.findLastVisibleItemPosition());
                totalVisible = layoutManager.getItemCount();
                lastVisible = layoutManager.findLastVisibleItemPosition();
                if(!isLoading && totalVisible <= (lastVisible + PAGINATE)){
                    System.out.println("TRUE");
                    isLoading = true;
                }
            }
        });
    }

    public void setLoaded(){
        this.isLoading = false;
    }

    @Override
    public int getItemCount() {
        return orders.size() + 1;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == TYPE_ITEM)
        {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_order,parent,false);
            return new ItemViewHolder(view);
        }
        else if(viewType == TYPE_LOADING)
        {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.load_more,parent,false);
            return new LoadingViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof ProductsAdapter.ItemViewHolder)
        {
            final Order model = orders.get(position);
            final ItemViewHolder viewHolder = (ItemViewHolder) holder;
            viewHolder.txtAddress.setText(model.getAddress());
            viewHolder.txtPhone.setText(model.getPhone());
            viewHolder.txtTime.setText(model.getCreated());

            LinearLayout.LayoutParams layout =
                    new LinearLayout.LayoutParams(AppSizes.getScreenWidth()/6, AppSizes.getScreenWidth()/6);
            viewHolder.ivStatus.setLayoutParams(layout);

            viewHolder.txtStatus.setText(AppContracts.orderStatus(model.getStatus()));
            viewHolder.iconStatus.setBackgroundResource(AppContracts.orderStatusBackground(model.getStatus()));
            viewHolder.cardView.setBackgroundResource(AppContracts.orderStatusBorder(model.getStatus()));
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Router.navigator(Router.INVOICE, activity, new String[]{String.valueOf(model.getId())});
                }
            });
        }
        else if(holder instanceof ProductsAdapter.LoadingViewHolder)
        {
            ProductsAdapter.LoadingViewHolder loadingViewHolder = (ProductsAdapter.LoadingViewHolder)holder;
            loadingViewHolder.progressBar.setIndeterminate(true);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position >= orders.size() ? TYPE_LOADING : TYPE_ITEM;
    }

    class LoadingViewHolder extends RecyclerView.ViewHolder
    {
        public ProgressBar progressBar;
        public LoadingViewHolder(View itemView) {
            super(itemView);
            progressBar = (ProgressBar)itemView.findViewById(R.id.progress_circular);
        }
    }
    public class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView txtAddress;
        TextView txtPhone;
        TextView txtStatus;
        TextView txtTime;
        LinearLayout iconStatus;
        ImageView ivStatus;
        CardView cardView;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            iconStatus = itemView.findViewById(R.id.ll_icon_status_order_item);
            cardView = itemView.findViewById(R.id.cardViewOrderItem);
            txtAddress = itemView.findViewById(R.id.txt_address_order_item);
            txtPhone = itemView.findViewById(R.id.txt_phone_order_item);
            txtStatus = itemView.findViewById(R.id.txt_status_order_item);
            txtTime = itemView.findViewById(R.id.txt_create_order_item);
            ivStatus = itemView.findViewById(R.id.iv_order_item);
        }
    }
}
