package com.knight.f_interesting.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.knight.f_interesting.R;
import com.knight.f_interesting.api.AppClient;
import com.knight.f_interesting.models.Coupon;

import java.util.List;

public class CouponAdapter extends RecyclerView.Adapter<CouponAdapter.ViewHolder> {
    private List<Coupon> coupons;

    public CouponAdapter(List<Coupon> coupons){
        this.coupons =coupons;
    }

    public void changeData(List<Coupon> coupons){
        this.coupons = coupons;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CouponAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_coupon, parent, false);
        return new CouponAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CouponAdapter.ViewHolder holder, int position) {
        final Coupon item = coupons.get(position);
        Glide.with(holder.itemView).load(AppClient.url()
                + item.getImage())
                .into(holder.image);
        holder.txtTitle.setText(item.getTitle());
        holder.txtCode.setText(item.getCode());
        holder.txtExpired.setText(item.getExpired().substring(0, 10));
//        holder.itemView.setLayoutParams(
//                new FrameLayout.LayoutParams(AppSizes.getScreenWidth()  *3 / 5,
//                        AppSizes.getScreenWidth() * 2 / 5));
    }

    @Override
    public int getItemCount() {
        return coupons.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView image;
        TextView txtTitle;
        TextView txtCode;
        TextView txtExpired;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.iv_coupon_item);
            txtTitle = itemView.findViewById(R.id.txt_title_coupon_item);
            txtExpired = itemView.findViewById(R.id.txt_expired_coupon_item);
            txtCode = itemView.findViewById(R.id.txt_code_coupon_item);
        }
    }
}
