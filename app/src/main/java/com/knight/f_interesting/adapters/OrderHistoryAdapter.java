package com.knight.f_interesting.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.knight.f_interesting.R;
import com.knight.f_interesting.models.Order;
import com.knight.f_interesting.utils.AppContracts;
import com.knight.f_interesting.utils.AppSizes;
import com.knight.f_interesting.utils.Router;

import java.util.List;

public class OrderHistoryAdapter extends RecyclerView.Adapter<OrderHistoryAdapter.ViewHolder> {

    private List<Order> orders;
    private Activity activity;

    public OrderHistoryAdapter(Activity activity, List<Order> orders){
        this.orders = orders;
        this.activity = activity;
    }

    public void refresh(List<Order> orders) {
        this.orders = orders;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_order, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final Order model = orders.get(position);
        holder.txtAddress.setText(model.getAddress());
        holder.txtPhone.setText(model.getPhone());
        holder.txtTime.setText(model.getCreated());

        LinearLayout.LayoutParams layout =
                new LinearLayout.LayoutParams(AppSizes.getScreenWidth()/6, AppSizes.getScreenWidth()/6);
        holder.ivStatus.setLayoutParams(layout);

        holder.txtStatus.setText(AppContracts.orderStatus(model.getStatus()));
        holder.iconStatus.setBackgroundResource(AppContracts.orderStatusBackground(model.getStatus()));
        holder.cardView.setBackgroundResource(AppContracts.orderStatusBorder(model.getStatus()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Router.navigator(Router.INVOICE, activity, new String[]{String.valueOf(model.getId())});
            }
        });
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtAddress;
        TextView txtPhone;
        TextView txtStatus;
        TextView txtTime;
        LinearLayout iconStatus;
        ImageView ivStatus;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
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
