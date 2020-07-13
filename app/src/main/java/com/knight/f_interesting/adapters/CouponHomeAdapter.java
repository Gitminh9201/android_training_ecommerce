package com.knight.f_interesting.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.knight.f_interesting.R;
import com.knight.f_interesting.api.AppClient;
import com.knight.f_interesting.models.Coupon;
import com.knight.f_interesting.utils.AppSizes;

import java.util.List;

public class CouponHomeAdapter extends RecyclerView.Adapter<CouponHomeAdapter.ViewHolder> {

    private List<Coupon> coupons;

    public CouponHomeAdapter(List<Coupon> coupons){
        this.coupons =coupons;
    }

    public void changeData(List<Coupon> coupons){
        this.coupons = coupons;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_coupon_home, parent, false);
        return new CouponHomeAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Coupon item = coupons.get(position);
        Glide.with(holder.itemView).load(AppClient.url()
                + item.getImage())
                .into(holder.image);
        holder.itemView.setLayoutParams(
                new FrameLayout.LayoutParams(AppSizes.getScreenWidth()  *3 / 5,
                        AppSizes.getScreenWidth() * 2 / 5));
    }

    @Override
    public int getItemCount() {
        return coupons.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.iv_coupon_home);
        }
    }
}
