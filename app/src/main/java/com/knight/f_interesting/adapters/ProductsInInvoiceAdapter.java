package com.knight.f_interesting.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.knight.f_interesting.R;
import com.knight.f_interesting.api.AppClient;
import com.knight.f_interesting.models.OrderDetail;
import com.knight.f_interesting.utils.AppSizes;
import com.knight.f_interesting.utils.AppUtils;

import java.util.List;

public class ProductsInInvoiceAdapter extends RecyclerView.Adapter<ProductsInInvoiceAdapter.ViewHolder> {
    private List<OrderDetail> details;

    public ProductsInInvoiceAdapter(List<OrderDetail> details) {
        this.details = details;
    }

    public void changeData(List<OrderDetail> details) {
        this.details = details;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_product_invoice, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final OrderDetail item = details.get(position);
        Glide.with(holder.itemView.getContext()).load(AppClient.url()
                + item.getProduct().getImage()
        ).into(holder.imageView);
        holder.imageView.setLayoutParams(new LinearLayout.LayoutParams(
                AppSizes.getScreenWidth() / 4, AppSizes.getScreenWidth() / 4));
        holder.txtPrice.setText(AppUtils.currencyVN(item.getProduct().getPrice() * item.getQuantity()));
        holder.txtName.setText(item.getProduct().getTitle());
        if(item.getQuantity() < 10) holder.txtQty.setText("0" + String.valueOf(item.getQuantity()));
        else holder.txtQty.setText(String.valueOf(item.getQuantity()));
    }

    @Override
    public int getItemCount() {
        return details.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView txtPrice;
        TextView txtName;
        TextView txtQty;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.iv_product_invoice);
            txtPrice = itemView.findViewById(R.id.txt_price_invoice);
            txtQty = itemView.findViewById(R.id.txt_qty_invoice);
            txtName = itemView.findViewById(R.id.txt_name_invoice);
        }
    }
}
