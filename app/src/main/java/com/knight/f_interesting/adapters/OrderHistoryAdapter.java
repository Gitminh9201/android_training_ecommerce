package com.knight.f_interesting.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.knight.f_interesting.R;
import com.knight.f_interesting.models.Order;

import java.util.List;

public class OrderHistoryAdapter extends RecyclerView.Adapter<OrderHistoryAdapter.ViewHolder> {

    private List<Order> orders;
    private Context context;

    public OrderHistoryAdapter(Context context, List<Order> orders){
        this.orders = orders;
        this.context = context;
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
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Order model = orders.get(position);
        holder.txtAddress.setText(model.getAddress());
        holder.txtPhone.setText(model.getPhone());
        holder.txtTime.setText(model.getCreated());
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
        ImageView ivStatus;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtAddress = itemView.findViewById(R.id.txt_address_order_item);
            txtPhone = itemView.findViewById(R.id.txt_phone_order_item);
            txtStatus = itemView.findViewById(R.id.txt_status_order_item);
            txtTime = itemView.findViewById(R.id.txt_create_order_item);
            ivStatus = itemView.findViewById(R.id.iv_order_item);
        }
    }
}
